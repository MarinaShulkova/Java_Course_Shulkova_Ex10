public class MenuException extends Throwable {
    private int menu;

    public MenuException (int menu) {
        this.menu = menu;
    }

    public int getMenu() {
        return menu;
    }
}