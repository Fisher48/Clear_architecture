package ru.fisher.Task17.models;

import java.util.function.Function;

public class MonadState<S, A> {

    // Основная функция для работы над состояниями
    private final Function<S, Result<A, S>> runState;

    public MonadState(Function<S, Result<A, S>> runState) {
        this.runState = runState;
    }

    /**
     * bind (>>=) - оператор связывания монад
     * m >>= f = из m извлекаем значение, применяем f, получаем новую монаду
     */
    public <B> MonadState<S, B> bind(Function<A, MonadState<S, B>> f) {
        return new MonadState<>(state -> {
            Result<A, S> result = runState.apply(state); // выполняем текущее действие
            MonadState<S, B> nextState = f.apply(result.value()); // берём результат A и строим следующее действие
            return nextState.runState.apply(result.state()); // передаём новое состояние дальше
        });
    }

    // then (>>) - последовательное выполнение (игнорируем результат)
    public <B> MonadState<S, B> then(MonadState<S, B> next) {
        return bind(ignored -> next);
    }

    // unit (return) - создание монады из значения
    public static <S, A> MonadState<S, A> unit(A value) {
        return new MonadState<>(state -> new Result<>(value, state));
    }

    // modify - модифицирует состояние
    public static <S> MonadState<S, Void> modify(Function<S, S> f) {
        return new MonadState<>(state -> new Result<>(null, f.apply(state)));
    }

    // Запуск вычисления с начальным состоянием
    public Result<A, S> run(S initialState) {
        return runState.apply(initialState);
    }

    // Результат вычисления (значение + состояние)
    public record Result<A, S>(A value, S state) {}
}
