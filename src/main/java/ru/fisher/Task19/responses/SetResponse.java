package ru.fisher.Task19.responses;

import ru.fisher.Task19.State;

public record SetResponse(State newMode, boolean success) {}
