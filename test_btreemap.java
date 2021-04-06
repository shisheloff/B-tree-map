public class test_btreemap {
    public static void main(String[] args) {
        btreemap<Integer, Integer> btreemap = new btreemap<>();
        for (int j = 0; j < 30; j++) {
            btreemap.put(j, j);
        }
        System.out.println("--------------------------------\n" + "Вывод дерева");
        System.out.print(btreemap.toString());
        System.out.println("--------------------------------");
        System.out.println("Поиск по ключу 7: " + btreemap.get(7));
        System.out.println("Поиск по ключу 43: " + btreemap.get(43));
        System.out.println("Поиск по ключу 32: " + btreemap.get(32));
        System.out.println("--------------------------------");
        System.out.println("Проверка на существование узла с ключем 17: " + btreemap.isContains(17));
        System.out.println("Проверка на существование узла с ключем 97: " + btreemap.isContains(97));
        System.out.println("--------------------------------");
        btreemap.deleteTree();
        System.out.println("Проверка на пустоту дерева: " + btreemap.isEmpty());
    }
}
