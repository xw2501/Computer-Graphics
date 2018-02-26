package c2g2.engine;

public interface IGameLogic {

    void init(Window window) throws Exception;
    
    void input(Window window, MouseInput mouseInput);

    void update(float interval, MouseInput mouseInput, Window window) throws Exception;
    
    void render(Window window);
    
    void cleanup();
}