package KirbyGame_HVL.git.entities.States;

public class StateManager {

    private State currentState;

    public void setState (State state) {

        if (state != null) {
            state.end();
        }
        currentState = state;
        currentState.start();

    }

    public void update (float delta) {

        currentState.update(delta);
    }
}
