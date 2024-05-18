package com.itheima.day1805.run;
import com.itheima.day1805.bean.Business;
import com.itheima.day1805.bean.Customer;
import com.itheima.day1805.bean.Movie;
import com.itheima.day1805.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MovieSystem {
    /**STEP1:定義系統的數據容器用戶存儲數據: 1.存很多用戶(用戶包含客戶對象跟商家對象)*/
    public static final List<User> ALL_USERS = new ArrayList<>();//final可以將變數變為常量，讓變數地址永遠不會變但是可以往裡面加東西
    /**STEP2:存儲系統全部商家&排片訊息
     * 商家1 = [電影1, 電影2...]
     * 商家2 = [電影3, 電影4...]
     * 會不斷往集合內部增減上映電影，所以用List<Movie>*/
    public static final Map<Business, List<Movie>> ALL_MOVIES = new HashMap<>();

    /**定義1個靜態的User類型的變量，記住當前登入成功的用戶對象[這裡不想變成將u傳遞給方法，而是透過靜態變量。所以透過loginUser = u;傳進來這裡]*/
    public static User loginUser;
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public static final Logger LOGGER = LoggerFactory.getLogger("MovieSystem.class");

    /**STEP3:準備一些測試數據*/
    static{
        Customer c = new Customer();
        c.setLoginName("zyf888");
        c.setPassword("123456");
        c.setUserName("Max");
        c.setSex('男');
        c.setMoney(10000);
        c.setPhone("11010");
        ALL_USERS.add(c);
        Customer c1 = new Customer();
        c.setLoginName("gz188");
        c.setPassword("123456");
        c.setUserName("Mary");
        c.setSex('女');
        c.setMoney(20000);
        c.setPhone("111111");
        ALL_USERS.add(c1);

        Business b = new Business();
        b.setLoginName("baozugong888");
        b.setPassword("123456");
        b.setUserName("包租公");
        b.setMoney(0);
        b.setSex('男');
        b.setPhone("110110");
        b.setShopName("cinema1");
        b.setShopAddress("Mars floor 2");
        ALL_USERS.add(b);
        //注意:商家一定要加入到店鋪排片訊息中。
        List<Movie> movies = new ArrayList<>();
        ALL_MOVIES.put(b, movies);//b = []
        Business b2 = new Business();
        b2.setLoginName("baozupo888");
        b2.setPassword("123456");
        b2.setUserName("包租婆");
        b2.setMoney(0);
        b2.setSex('女');
        b2.setPhone("110110");
        b2.setShopName("cinema2");
        b2.setShopAddress("Jupitor floor 2");
        ALL_USERS.add(b2);
        List<Movie> movies3 = new ArrayList<>();
        ALL_MOVIES.put(b2, movies3);
    }

    public static void main(String[] args) {
        //ALL_USERS = null;//會報錯
        showMain();
    }

    public static final Scanner SYS_SC = new Scanner(System.in);
    private static void showMain() {
        while (true) {
            System.out.println("=================電影首頁==============");
            System.out.println("1.登錄");
            System.out.println("2.用戶註冊");
            System.out.println("3.商家註冊");
            System.out.println("請輸入操作命令");
            String command = SYS_SC.nextLine();
            switch(command){
                case "1"://登錄
                    login();
                    break;//==>幹掉switch功能，就可以回到電影首頁執行登錄/用戶註冊/商家註冊...
                case "2"://用戶註冊
                    break;
                case "3"://商家註冊
                    break;
                default:
                    System.out.println("命令有誤請確認");
            }
        }
    }

    private static void login() {
        while (true) {
            System.out.println("請輸入登入名稱: ");
            String loginName = SYS_SC.nextLine();
            System.out.println("請輸入登入密碼: ");
            String password = SYS_SC.nextLine();
            //STEP1:根據登錄名稱去查詢用戶對象(可能是客戶或商家)
            User u = getUserByLoginName(loginName);
            //STEP2.判斷用戶對象是否存在(若存在代表登入名稱正確)
            if(u != null){
                //STEP3.比對密碼
                if(u.getPassword().equals(password)){
                    //登錄成功
                    loginUser = u;//記住成功登入的用戶
                    LOGGER.info(u.getUserName()+"登錄了系統");
                    //判斷是用戶登錄還是商家登錄==>可透過判斷是什麼型別來區分[有Customer跟Business]==>instanceof可判斷型別
                    if (u instanceof Customer){
                        showCustomerMain();//當前登入的是普通用戶
                    }else{
                        showBusinessMain();//當前登入的是商家用戶
                    }
                    return;//因為登入成功之後還是要跳離開login()，並且回去到主頁面
                }else{
                    System.out.println("密碼錯誤");
                }
            }else{
                System.out.println("登入名稱錯誤，請確認");
            }
        }
    }

    private static void showCustomerMain() {//客戶操作介面
        while(true){
            System.out.println("=======客戶操作介面，請選擇要操作功能========");
            System.out.println(loginUser.getUserName()+(loginUser.getSex()=='男'? "先生":"小姐")+" Welcome");
            System.out.println("case1.展示全部影片訊息功能");
            System.out.println("case2.根據電影名稱查詢電影訊息");
            System.out.println("case3.評分功能");
            System.out.println("case4.購票功能");
            System.out.println("case5.退出系統");
            System.out.println("請輸入要操作的指令");
            String command = SYS_SC.nextLine();
            switch(command){
                case "1":
                    showAllMovies();
                    break;
                case "2":

                    break;
                case "3":

                    break;
                case "4":
                    buyMovie();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("不存在此指令");
                    break;
            }
        }
    }

    private static void buyMovie() {//用戶購票功能
        showAllMovies();
        System.out.println("=======用戶購票功能========");
        while (true) {
            System.out.println("輸入需要買票的門店");
            String shopName = SYS_SC.nextLine();
            //1.查詢是否存在該商家
            Business business = getBusinessByShopName(shopName);
            if(business == null){
                System.out.println("沒有該店鋪，請確認");
            }else{
                //2.此商家全部的排片
                List<Movie> movies = ALL_MOVIES.get(business);
                //3.判斷是否存在上映的電影
                if(movies.size()>0){
                    //4.開始進行選片購買
                    while (true) {
                        System.out.println("請輸入需要購買電影名稱: ");
                        String movieName = SYS_SC.nextLine();
                        //去當前商家下，查詢該電影對象
                        Movie movie = getMovieByShopAndName(business, movieName);
                        if(movie != null){
                            //開始購買
                            while(true){
                                System.out.println("請輸入需要購買電影票數: ");
                                String number = SYS_SC.nextLine();
                                int buyNumber = Integer.valueOf(number);
                                //判斷電影票數量是否足夠
                                if(movie.getNumber() >= buyNumber){
                                    //可以購買了
                                    //當前需要花費的金額
                                    double money = BigDecimal.valueOf(movie.getPrice()).multiply(BigDecimal.valueOf(buyNumber))
                                            .doubleValue();
                                    if(loginUser.getMoney() >= money){
                                        //終於可以買票了
                                        System.out.println("您成功購買"+movie.getName()+buyNumber+"張票，其總金額是"+money);
                                        //更新自己&商家的金額
                                        loginUser.setMoney(loginUser.getMoney()-money);
                                        business.setMoney(business.getMoney()+money);
                                        movie.setNumber(movie.getNumber() - buyNumber);
                                        return;//結束方法
                                    }else{//錢不夠
                                        System.out.println("是否繼續買票(y/n)?");
                                        String command = SYS_SC.nextLine();
                                        switch (command){
                                            case "y":
                                                break;
                                            default:
                                                System.out.println("好的");
                                                return;
                                        }
                                    }
                                }else{
                                    System.out.println("您當前最多可以購買: "+movie.getNumber());
                                    System.out.println("是否繼續買票(y/n)?");
                                    String command = SYS_SC.nextLine();
                                    switch (command){
                                        case "y":
                                            break;
                                        default:
                                            System.out.println("好的");
                                            return;
                                    }
                                }
                            }
                        }else{
                            System.out.println("電影名稱有毛病");
                        }
                    }
                }else{
                    System.out.println("此電影院關門了，是否繼續買票(y/n)");
                    String command = SYS_SC.nextLine();
                    switch (command){
                        case "y":
                            break;
                        default:
                            System.out.println("好的");
                            return;
                    }
                }
            }
        }
    }

    public static Movie getMovieByShopAndName(Business business, String name){
        List<Movie> movies = ALL_MOVIES.get(business);
        for (Movie movie : movies){
            if(movie.getName().contains(name)){
                return movie;
            }
        }
        return null;
    }

    public static Business getBusinessByShopName(String shopName){//查詢是否存在該商家功能
        Set<Business> businesses = ALL_MOVIES.keySet();
        for(Business business:businesses){
            if(business.getShopName().equals(shopName)){
                return business;
            }
        }
        return null;
    }
    private static void showAllMovies() {//展示全部影片訊息功能
        System.out.println("=======展示全部影片訊息功能========");
        ALL_MOVIES.forEach((business, movies) -> {
            System.out.println(business.getShopName()+"\t\t電話: "+business.getPhone()+"\t\t地址: "+business.getShopAddress());
            System.out.println("\t\t\t片名\t\t\t主演\t\t\t時長\t\t\t評分\t\t\t票價\t\t\t餘票數量\t\t\t放映時間");
            for(Movie movie : movies){
                System.out.println("\t\t\t"+movie.getName()+"\t\t\t"+movie.getActor()+"\t\t"+movie.getTime()+"\t\t"+movie.getScore()
                        +"\t\t"+movie.getPrice()+"\t\t"+movie.getNumber()+"\t\t"+sdf.format(movie.getStartTime()));
            }
        });
    }

    private static void showBusinessMain() {//商家操作介面
        while(true){
            System.out.println("=======商家操作介面，請選擇要操作功能========");
            System.out.println(loginUser.getUserName()+(loginUser.getSex()=='男'? "先生":"小姐")+" Welcome");
            System.out.println("case1.展示詳情");
            System.out.println("case2.上架電影");
            System.out.println("case3.下架電影");
            System.out.println("case4.修改電影");
            System.out.println("case5.退出");
            System.out.println("請輸入要操作的指令");
            String command = SYS_SC.nextLine();
            switch(command){
                case "1":
                    showBusinessInfos();
                    break;
                case "2":
                    addMovie();
                    break;
                case "3":
                    deleteMovie();
                    break;
                case "4":
                    updateMovie();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("不存在此指令");
                    break;
            }
        }
    }

    private static void updateMovie() {//影片修改功能
        System.out.println("===============影片修改功能============");
        Business business = (Business) loginUser;
        List<Movie> movies = ALL_MOVIES.get(business);
        if(movies.size() == 0){
            System.out.println("當前無影片可以修改");
            return;
        }
        while (true) {
            System.out.println("請輸入需要修改的電影名稱");
            String movieName = SYS_SC.nextLine();
            //去查詢有沒有這個影片對象
            Movie movie = getMovieByName(movieName);
            if(movie != null){
                //修改它
                System.out.println("請輸入修改後的片名: ");
                String name = SYS_SC.nextLine();
                System.out.println("請輸入修改後的主演: ");
                String actor = SYS_SC.nextLine();
                System.out.println("請輸入修改後的時長: ");
                String time = SYS_SC.nextLine();
                System.out.println("請輸入修改後的票價: ");
                String price = SYS_SC.nextLine();
                System.out.println("請輸入修改後的票數: ");
                String totalNumber = SYS_SC.nextLine();
                while (true) {
                    try {
                        System.out.println("請輸入修改後的影片放映時間: ");
                        String stime = SYS_SC.nextLine();
                        movie.setName(name);
                        movie.setActor(actor);
                        movie.setPrice(Double.valueOf(price));
                        movie.setTime(Double.valueOf(time));
                        movie.setNumber(Integer.valueOf(totalNumber));
                        movie.setStartTime(sdf.parse(stime));
                        System.out.println("您已經成功修改了: "+movie.getName());
                        showBusinessInfos();//查詢1次比較安心
                        return;//直接退出去此方法[private static void updateMovie()]
                    } catch (Exception e) {
                        e.printStackTrace();
                        LOGGER.error("時間解析出了毛病");//如果繃了就讓他重新操作==>所以用死循環
                    }
                }
            }else{
                System.out.println("你的店鋪中沒有該電影。請問繼續修改其他電影嗎");
                String command = SYS_SC.nextLine();
                switch (command){
                    case "y":
                        break;
                    default:
                        System.out.println("好的");
                        return;
                }
            }
        }
    }

    private static void deleteMovie() {//影片下架功能
        System.out.println("===============影片下架功能============");
        Business business = (Business) loginUser;
        List<Movie> movies = ALL_MOVIES.get(business);
        if(movies.size() == 0){
            System.out.println("當前無影片可以下架");
            return;
        }
        //讓用戶選擇需要下架的電影名稱
        while (true) {
            System.out.println("請輸入需要下架的電影名稱");
            String movieName = SYS_SC.nextLine();
            //去查詢有沒有這個影片對象
            Movie movie = getMovieByName(movieName);
            if(movie != null){
                movies.remove(movie);
                System.out.println("您已經成功下架: "+movie.getName());
                showBusinessInfos();//查詢1次比較安心
                return;
            }else{
                System.out.println("你的店鋪中沒有該電影。請問繼續下架其他電影嗎");
                String command = SYS_SC.nextLine();
                switch (command){
                    case "y":
                        break;
                    default:
                        System.out.println("好的");
                        return;
                }
            }
        }
    }
    public static Movie getMovieByName(String movieName){//這是去查詢目前商家內的排片
        Business business = (Business) loginUser;
        List<Movie> movies = ALL_MOVIES.get(business);
        for (Movie movie : movies) {
            if(movie.getName().contains(movieName)){
                return movie;
            }
        }
        return null;
    }

    private static void addMovie() {//商家進行電影上架
        System.out.println("=============影片上架功能==============");
        Business business = (Business) loginUser;
        List<Movie> movies = ALL_MOVIES.get(business);
        System.out.println("請輸入新片名: ");
        String name = SYS_SC.nextLine();
        System.out.println("請輸入主演: ");
        String actor = SYS_SC.nextLine();
        System.out.println("請輸入時長: ");
        String time = SYS_SC.nextLine();
        System.out.println("請輸入票價: ");
        String price = SYS_SC.nextLine();
        System.out.println("請輸入票數: ");
        String totalNumber = SYS_SC.nextLine();
        while (true) {
            try {
                System.out.println("請輸入影片放映時間: ");
                String stime = SYS_SC.nextLine();
                //封裝成電影對象加入到集合中
                Movie movie = new Movie(name,actor,Double.valueOf(time),Double.valueOf(price)
                        ,Integer.valueOf(totalNumber),sdf.parse(stime));
                movies.add(movie);
                System.out.println("您已經成功上架了: "+movie.getName());
                return;
            } catch (ParseException e) {
                e.printStackTrace();
                LOGGER.error("時間解析出了毛病");//如果繃了就讓他重新操作==>所以用死循環
            }
        }
    }

    private static void showBusinessInfos() {//展示當前商家的資訊
        System.out.println("===============商家詳情介面=========================");
        LOGGER.info(loginUser.getUserName()+"商家，正在看自己的詳情");
        //根據商家對象(就是登入的用戶loginUser)，作為Map集合的鍵 提取對應的值就是排片訊息[變量:ALL_MOVIES]
        Business business = (Business) loginUser;
        System.out.println(business.getShopName()+"\t\t電話: "+business.getPhone()+"\t\t地址: "+business.getShopAddress());
        List<Movie> movies = ALL_MOVIES.get(business);
        if(movies.size()>0){
            System.out.println("片名\t\t\t主演\t\t\t時長\t\t\t評分\t\t\t票價\t\t\t餘票數量\t\t\t放映時間");
            for(Movie movie : movies){
                System.out.println(movie.getName()+"\t\t\t"+movie.getActor()+"\t\t"+movie.getTime()+"\t\t"+movie.getScore()
                        +"\t\t"+movie.getPrice()+"\t\t"+movie.getNumber()+"\t\t"+sdf.format(movie.getStartTime()));
            }
        }else{
            System.out.println("您的店舖目前無片在上映");
        }
    }

    public static User getUserByLoginName(String loginName){//loginName從SYS_SC.nextLine得到
        for (User user : ALL_USERS) {
            if(user.getLoginName().equals(loginName)){
                return user;
            }
        }
        return null;//查無此用戶
    }

}
