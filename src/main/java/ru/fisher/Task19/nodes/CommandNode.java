package ru.fisher.Task19.nodes;


import ru.fisher.Task19.Result;
import ru.fisher.Task19.RobotState;
import ru.fisher.Task19.interfaces.Node;

import java.util.function.Function;

public abstract class CommandNode<R> implements Node {
    protected final Function<R, Node> next;

    protected CommandNode(Function<R, Node> next) {
        this.next = next;
    }

    // главный метод — выполняем текущий узел: возвращаем Result (response + newState)
    protected abstract Result<R> run(RobotState state);

    @Override
    public RobotState interpret(RobotState state) {
        Result<R> res = run(state);
        Node nextNode = (next != null) ? next.apply(res.response()) : null;
        if (nextNode == null) {
            return res.newState();
        } else {
            return nextNode.interpret(res.newState());
        }
    }
}