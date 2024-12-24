package KirbyGame_HVL.git.entities.States;

public class StateManager {

    private State currentState;

    public StateManager () {

    }

    public void setState (State state) {

        if (currentState != null) {
            currentState.end();
        }
        currentState = state;
        currentState.start();

    }

    public State getState() {
        return currentState;
    }

    public void start() {
        currentState.start();
    }

    public void update (float delta) {

        currentState.update(delta);
    }

    public void end() {
        currentState.end();
    }
}
