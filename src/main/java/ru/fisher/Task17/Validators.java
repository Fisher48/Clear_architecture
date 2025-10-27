package ru.fisher.Task17;


import ru.fisher.Task17.models.State;

public class Validators {

    public static class MoveResponse {
        public static final String OK = "MOVE_OK";
        public static final String BARRIER = "HIT_BARRIER";

        // Проверка корректности позиции
        public static MoveCheckResult checkPosition(double newX, double newY) {
            double constrainedX = Math.max(0, Math.min(100, newX));
            double constrainedY = Math.max(0, Math.min(100, newY));

            String result = (newX == constrainedX && newY == constrainedY) ? OK : BARRIER;

            return new MoveCheckResult(constrainedX, constrainedY, result);
        }

        public record MoveCheckResult(double x, double y, String result) {}
    }

    public static class SetStateResponse {
        public static final String OK = "STATE_OK";
        public static final String NO_WATER = "OUT_OF_WATER";
        public static final String NO_SOAP = "OUT_OF_SOAP";


        // Проверка доступности ресурсов
        public static String checkResources(State newMode, int waterLevel, int soapLevel) {
            if (newMode == State.water && waterLevel <= 0) {
                return SetStateResponse.NO_WATER;
            } else if (newMode == State.soap && soapLevel <= 0) {
                return SetStateResponse.NO_SOAP;
            }
            return SetStateResponse.OK;
        }
    }

}
