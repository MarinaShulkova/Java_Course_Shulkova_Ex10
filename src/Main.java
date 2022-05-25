import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class Main {

    static List<Student> student_group = new ArrayList<>();

    public static void main (String[] args) throws MenuException {
        menu(print_menu());
    }

    // Процедура работы с меню
    public static void menu(int menu_item) throws MenuException {
        switch (menu_item) {
            case 1:  //меню 1 - добавление студента
                addStudent();
                menu(print_menu());
                break;
            case 2: //меню 2 - распечатка списка студентов
                Comparator<Student> fio_comparator = Comparator.comparing(student -> student.fio); // компоратор по ФИО
                Comparator<Student> age_comparator = Comparator.comparingInt(student -> student.age); // компаратор по возрасту
                student_group.sort(fio_comparator.thenComparing(age_comparator));
                for (Student student: student_group) {
                    System.out.println("FIO: " + student.fio + " | Age: " + student.age + " | Group: " + student.group);
                }

                menu(print_menu());
                break;
            case 3: //меню 3 - выход из программы
                System.exit(404);
                break;
        }
    }


    // Вывод меню в консоль и возвращаем выбранное значение из консоли
    public static int print_menu () throws MenuException {
        System.out.println(
                "1 Enter student \n" +
                        "2 Print group's \n" +
                        "3 Exit");

        System.out.println("Choose menu item: ");
        Scanner scr = new Scanner(System.in);
        int menu_item = scr.nextInt();
        try {
            if (menu_item <= 0 || menu_item > 3) {
                throw new MenuException(menu_item);
            }
        } catch (MenuException me) {
            System.out.println(me.getMenu() + " - Invalid value, please try again!");
            System.out.println();
            menu(print_menu());

        }
        return menu_item;
    }

    // Процедура добавления студента
    public static void addStudent () throws MenuException {
        Scanner scr = new Scanner(System.in);
        System.out.println("Enter FIO");
        String fio = scr.nextLine();
        System.out.println("Enter age");
        int age = scr.nextInt();
        System.out.println("Enter birth year");
        int year = scr.nextInt();

        Person person = new Person(fio,age,year);
        Predicate<Integer> a = age1 -> age > 17 && age < 40;
        Function<Person, Student> f = person1 -> {
            Student student = new Student();
            student.age = person.age;
            student.fio = person.fio;
            if (year<1995) student.group = "УбИН-01-22";
            else student.group = "УбИН-02-22";
            return student;
        };

        if (a.test(person.age))
            student_group.add(f.apply(person));
        else {
            System.out.println("The age less than 17 and greater than 40");
            System.out.println();
            menu(print_menu());
        }
    }
}
