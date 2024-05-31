package com.example.yacod.DataBase;

import com.example.yacod.Front;
import com.example.yacod.Logic.Crypto;
import com.example.yacod.Model.*;

import java.sql.*;
import java.util.ArrayList;

public class Postgre {
    static Connection data_connection;

    static Connection connection;
    static Statement data_statmt;

    static Statement statmt;
    static ResultSet data_resSet;
    public Postgre(String login, String pass) throws SQLException {
        data_connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/COD",login,pass);
        data_statmt = data_connection.createStatement();
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/COD","postgres","123");
        statmt = connection.createStatement();
    }

    public static String getRole() throws SQLException {
        data_resSet = data_statmt   .executeQuery("select rolname from pg_user\n" +
                "join pg_auth_members on (pg_user.usesysid=pg_auth_members.member)\n" +
                "join pg_roles on (pg_roles.oid=pg_auth_members.roleid)\n" +
                "where usename = current_user;");
        String role="";
        while (data_resSet.next()){
            role = data_resSet.getString("rolname");
        }
        return role;
    }

    public static ArrayList<COD> getAllCOD() throws SQLException{
        data_resSet = data_statmt.executeQuery("SELECT id_cod, (address_cod).adres as a, (address_cod).postindex as b,  tel_cod, num_empl_cod, more_cod\n" +
                "\tFROM public.\"Cod\";");
        ArrayList<COD> arrayList = new ArrayList<>();
        while (data_resSet.next()){
            String id = data_resSet.getString("id_cod");
            String address = data_resSet.getString("a");
            String index = data_resSet.getString("b");
            String tel = data_resSet.getString("tel_cod");
            String num = data_resSet.getString("num_empl_cod");
            String more = data_resSet.getString("more_cod");
            COD cod = new COD(id,address,tel,num,more,index);
            arrayList.add(cod);
        }
        return arrayList;
    }

    public static void addLegalClient(String name, String orgName, String inn, String kpp, String ogrn,
                                      String tel, String mail, String address) throws SQLException {
        statmt.execute("INSERT INTO public.\"Client_Legal\"(\n" +
                "\tname_client, orgname_client, inn_client, kpp_client, ogrn_client, " +
                "\ttel_client, mail_client, address_client)\n" +
                "\tVALUES ('"+name+"', '"+ orgName+"', '"+inn+"', '"+ kpp+"', '"+ ogrn+"', '"
                +tel+"', '"+mail+"', '"+address+"');");

    }
    public static void addPhysClient(String name, String tel, String inn, String mail) throws SQLException {
        statmt.execute("INSERT INTO public.\"Client_Individ\"(\n" +
                "\tname_client, tel_client, inn_client, mail_client)\n" +
                "\tVALUES ('"+name+"', '"+tel+"', '"+inn+"', '"+mail+"');");

    }

    public static void addEmpl(String post ,String name, String exp, String age, String login,
                               String pass, String zp) throws SQLException {

        System.out.println(post);
        statmt.execute("INSERT INTO public.\"Employee\"(\n" +
                "\tname_empl, post_empl, exp_empl, sal_empl, age_empl)\n" +
                "\tVALUES ('" + name + "', '" + post + "', '" + exp + "', '" + zp + "', '" + age + "');");
        data_resSet = statmt.executeQuery("SELECT * FROM public.\"Employee\" WHERE name_empl = '" + name + "';");
        String id = "Error";
        while (data_resSet.next()) {
            id = data_resSet.getString("id_empl");

        }
        if (!id.equals("Error")) {
            String passSalt = Crypto.hash(pass);
            statmt.execute("INSERT INTO public.\"User\"(\n" +
                    "\tlogin_user, pass_user, id_empl)\n" +
                    "\tVALUES ('" + login + "', '" + passSalt + "', '" + id + "');");
            statmt.execute("CREATE ROLE \"" + login + "\"\n" +
                    "                LOGIN\n" +
                    "                NOSUPERUSER \n" +
                    "                NOCREATEDB \n" +
                    "                NOCREATEROLE \n" +
                    "                INHERIT \n" +
                    "                NOREPLICATION\n" +
                    "                CONNECTION LIMIT -1 \n" +
                    "                PASSWORD '" + pass + "';\n");
            if (post.equals("Администратор"))
                statmt.execute("GRANT \"CODAdmin\" TO \"" + login + "\" WITH ADMIN OPTION;\n");
            else if (post.equals("Инженер"))
                statmt.execute("GRANT \"CODEngineer\" TO \"" + login + "\" WITH ADMIN OPTION;\n");
            else if (post.equals("Аналитик"))
                statmt.execute("GRANT \"CODAnalyst\" TO \"" + login + "\" WITH ADMIN OPTION;\n");
            else
                statmt.execute("GRANT \"CODManager\" TO \"" + login + "\" WITH ADMIN OPTION;\n");

        }
    }

    public static ArrayList<PhysClient> getAllPhysClient() throws SQLException{
        data_resSet = statmt.executeQuery("SELECT id_client, status_client, name_client, " +
                "tel_client, bonus_client, inn_client, mail_client\n" +
                "\tFROM public.\"Client_Individ\";");
        ArrayList<PhysClient> arrayList = new ArrayList<>();
        while (data_resSet.next()){
            String id = data_resSet.getString("id_client");
            String status = data_resSet.getString("status_client");
            String name = data_resSet.getString("name_client");
            String tel = data_resSet.getString("tel_client");
            String bonus = data_resSet.getString("bonus_client");
            String inn = data_resSet.getString("inn_client");
            String mail = data_resSet.getString("mail_client");
            PhysClient physClient = new PhysClient(id,name,status,tel,bonus,inn,mail);
            arrayList.add(physClient);
        }
        return arrayList;
    }
    public static PhysClient getPhysClientbyId(String id_phys) throws  SQLException {
        data_resSet = statmt.executeQuery("SELECT id_client, status_client, name_client, " +
                "tel_client, bonus_client, inn_client, mail_client\n" +
                "\tFROM public.\"Client_Individ\" WHERE id_client = '"+id_phys+"';");
        PhysClient physClient = null;
        while (data_resSet.next()){
            String id = data_resSet.getString("id_client");
            String status = data_resSet.getString("status_client");
            String name = data_resSet.getString("name_client");
            String tel = data_resSet.getString("tel_client");
            String bonus = data_resSet.getString("bonus_client");
            String inn = data_resSet.getString("inn_client");
            String mail = data_resSet.getString("mail_client");
            physClient = new PhysClient(id,name,status,tel,bonus,inn,mail);
        }
        return physClient;
    }
    public static void deletePhysClient(String id) throws SQLException {
        statmt.execute("DELETE FROM public.\"Client_Individ\"\n" +
                "\tWHERE id_client ='"+id+"';");
    }
    public static void updatePhysClient(String id, String name, String tel, String inn, String mail) throws SQLException {
        statmt.execute("UPDATE public.\"Client_Individ\"\n" +
                "\tSET  name_client='"+name+"', tel_client='"+tel+"', inn_client='"+inn+"', mail_client='"+mail+"'\n" +
                "\tWHERE id_client='"+id+"';");
    }
    public static ArrayList<LegalClient> getAllLegalClient() throws SQLException{
        data_resSet = statmt.executeQuery("SELECT id_client, status_client, name_client, " +
                "orgname_client, inn_client, kpp_client, ogrn_client, tel_client, mail_client, " +
                "address_client, bonus_client\n" +
                "\tFROM public.\"Client_Legal\";");
        ArrayList<LegalClient> arrayList = new ArrayList<>();
        while (data_resSet.next()){
            String id = data_resSet.getString("id_client");
            String status = data_resSet.getString("status_client");
            String name = data_resSet.getString("orgname_client");
            String tel = data_resSet.getString("tel_client");
            String bonus = data_resSet.getString("bonus_client");
            String inn = data_resSet.getString("inn_client");
            String mail = data_resSet.getString("mail_client");
            LegalClient physClient = new LegalClient(id,name,status,tel,bonus,inn,mail);
            arrayList.add(physClient);
        }
        return arrayList;
    }
    public static LegalClient getLegalClientbyId(String id_legal) throws SQLException {
        data_resSet = statmt.executeQuery("SELECT id_client, status_client, name_client, " +
                "orgname_client, inn_client, kpp_client, ogrn_client, tel_client, mail_client, " +
                "address_client, bonus_client\n" +
                "\tFROM public.\"Client_Legal\" where id_client ='"+id_legal+"';");
        LegalClient legalClient = null;
        while (data_resSet.next()){
            String id = data_resSet.getString("id_client");
            String orgname = data_resSet.getString("orgname_client");
            String tel = data_resSet.getString("tel_client");
            String inn = data_resSet.getString("inn_client");
            String mail = data_resSet.getString("mail_client");
            String kpp = data_resSet.getString("kpp_client");
            String name = data_resSet.getString("name_client");
            String ogrn = data_resSet.getString("ogrn_client");
            String address = data_resSet.getString("address_client");
            legalClient = new LegalClient(id,orgname,"",tel,"",inn,mail,name,address,kpp,ogrn);
        }
        return legalClient;
    }
    public static void deleteLegalClient(String id) throws SQLException {
        statmt.execute("DELETE FROM public.\"Client_Legal\"\n" +
                "\tWHERE id_client ='"+id+"';");
    }
    public static void updateLegalClient(String id, String name, String orgname, String inn, String kpp, String ogrn,
                                         String tel, String mail, String address) throws SQLException {
        statmt.execute("UPDATE public.\"Client_Legal\"\n" +
                "\tSET name_client='"+name+"', orgname_client='"+orgname+"', inn_client='"+inn+"', kpp_client='"+kpp+"'," +
                " ogrn_client='"+ogrn+"', tel_client='"+tel+"', mail_client='"+mail+"', address_client='"+address+"'\n" +
                "\tWHERE id_client='"+id+"';");
    }
    public static void addCod(String address, String index, String tel, String kol) throws SQLException {
        statmt.execute("INSERT INTO public.\"Cod\"(\n" +
                "\taddress_cod, tel_cod, num_empl_cod)\n" +
                "\tVALUES (ROW('"+address+"', '"+index+"'),  '"+tel+"', '"+kol+"');");
    }

    public static COD getCODbyID(String id_cod) throws SQLException {
        data_resSet = data_statmt.executeQuery("SELECT id_cod, (address_cod).adres as a, (address_cod).postindex as b,  tel_cod, num_empl_cod, more_cod\n" +
                "\tFROM public.\"Cod\" WHERE id_cod ='"+id_cod+"';");
        ArrayList<COD> arrayList = new ArrayList<>();
        COD cod = null;
        while (data_resSet.next()){
            String id = data_resSet.getString("id_cod");
            String address = data_resSet.getString("a");
            String index = data_resSet.getString("b");
            String tel = data_resSet.getString("tel_cod");
            String num = data_resSet.getString("num_empl_cod");
            String more = data_resSet.getString("more_cod");
            cod = new COD(id,address,tel,num,more,index);
        }
        return cod;
    }
    public static void updateCOD(String id, String address, String index, String tel, String kol) throws SQLException {
        statmt.execute("UPDATE public.\"Cod\"\n" +
                "\tSET address_cod=ROW('"+address+"', '"+index+"'), tel_cod='"+tel+"', num_empl_cod='"+kol+"'\n" +
                "\tWHERE id_cod='"+id+"';");
    }
    public static void deleteCOD(String id) throws SQLException {
        statmt.execute("DELETE FROM public.\"Cod\"\n" +
                "\tWHERE id_cod='"+id+"';");
    }

    public static void addService(String id,String os, String price, String hdd, String cpu, String ram ) throws SQLException{
        statmt.execute("INSERT INTO public.\"Equipment\"(\n" +
                "\tid_cod, name_equip, date_equip, price_equip, value_ssd_equip, cpu_equip, ram_equip)\n" +
                "\tVALUES ('"+id+"', '"+os+"', '2024', '"+price+"', '"+hdd+"', '"+cpu+"', '"+ram+"');");
    }
    public static ArrayList<Service> getAllService(String id_cod) throws SQLException {
        ArrayList<Service> arrayList = new ArrayList<>();
        data_resSet = statmt.executeQuery("SELECT id_equip, id_cod, name_equip, date_equip, price_equip, " +
                "status_equip, \"freeSpace_equip\", date_start_equip, date_end_equip, value_ssd_equip, cpu_equip, ram_equip\n" +
                "\tFROM public.\"Equipment\" WHERE id_cod ='"+id_cod+"';");
        while (data_resSet.next()){
            String id = data_resSet.getString("id_equip");
            String os = data_resSet.getString("name_equip");
            String price = data_resSet.getString("price_equip");
            String hdd = data_resSet.getString("value_ssd_equip");
            String cpu = data_resSet.getString("cpu_equip");
            String ram = data_resSet.getString("ram_equip");
            Service service = new Service(id,os,ram,hdd,cpu,price);
            arrayList.add(service);
        }
        return arrayList;
    }
    public static void deleteService(String id_service) throws SQLException {
        statmt.execute("DELETE FROM public.\"Order\"\n" +
                "\tWHERE id_equip ='"+id_service+"';");
        statmt.execute("DELETE FROM public.\"Equipment\"\n" +
                "\tWHERE id_equip ='"+id_service+"';");
    }
    public static void updateService(String id_service,String os, String price, String hdd,
                                     String cpu, String ram) throws SQLException {
        statmt.execute("UPDATE public.\"Equipment\"\n" +
                "\tSET name_equip='"+os+"', price_equip='"+price+"', value_ssd_equip='"+hdd+"', " +
                "cpu_equip='"+cpu+"', ram_equip='"+ram+"'\n" +
                "\tWHERE id_equip='"+id_service+"';");
    }
    public static Service getServicebyID(String id_service) throws SQLException {
        data_resSet =statmt.executeQuery("SELECT * FROM public.\"Equipment\"\n" +
                "WHERE id_equip ='"+id_service+"';");
        Service service = null;
        while (data_resSet.next()){
            String os = data_resSet.getString("name_equip");
            String price = data_resSet.getString("price_equip");
            String hdd = data_resSet.getString("value_ssd_equip");
            String cpu = data_resSet.getString("cpu_equip");
            String ram = data_resSet.getString("ram_equip");
            service = new Service(id_service,os,ram,hdd,cpu,price);
        }
        return service;
    }
    public static String[] getCODAddress() throws SQLException {
        ArrayList<String> arrayList = new ArrayList<>();
        data_resSet = statmt.executeQuery("SELECT (address_cod).adres as a\n" +
                "\tFROM public.\"Cod\";");
        while (data_resSet.next()){
            String name = data_resSet.getString("a");
            arrayList.add(name);
        }
        String[] mas = new String[arrayList.size()];
        for(int i=0;i<arrayList.size();i++){
            mas[i] = arrayList.get(i);
        }
        return mas;
    }

    public static String[] getEmployeeNameMin() throws SQLException {
        ArrayList<String> arrayList = new ArrayList<>();
        data_resSet = statmt.executeQuery("SELECT name_empl as a\n" +
                "\tFROM public.\"Employee\" WHERE post_empl ='Менеджер';");
        while (data_resSet.next()){
            String name = data_resSet.getString("a");
            arrayList.add(name);
        }
        String[] mas = new String[arrayList.size()];
        for(int i=0;i<arrayList.size();i++){
            mas[i] = arrayList.get(i);
        }
        return mas;
    }
    public static String getEmployeeIDbyName(String name) throws SQLException {
        String id = "";
        data_resSet = statmt.executeQuery("SELECT id_empl, name_empl, post_empl, exp_empl, sal_empl, age_empl, more_empl\n" +
                "\tFROM public.\"Employee\" WHERE name_empl = '"+name+"';");
        while (data_resSet.next()){
            id = data_resSet.getString("id_empl");
        }
        return id;
    }
    public static String getCodIDbyAddress(String address) throws SQLException {
        String id = "";
        data_resSet = statmt.executeQuery("SELECT *\n" +
                "\tFROM public.\"Cod\" where (address_cod).adres = '"+address+"';;");
        while (data_resSet.next()){
            id = data_resSet.getString("id_cod");
        }
        return id;
    }
    public static void addWork(String cod, String employee) throws SQLException {
        String id_empl = Postgre.getEmployeeIDbyName(employee);
        String id_cod = Postgre.getCodIDbyAddress(cod);
        statmt.execute("INSERT INTO public.\"Cod_Employee\"(\n" +
                "\tid_empl, id_cod)\n" +
                "\tVALUES ('"+id_empl+"', '"+id_cod+"');");
    }
    public static ArrayList<Employee> getAllEmployeeMin(String id_cod) throws SQLException {
        ArrayList<Employee> arrayList = new ArrayList<>();
        data_resSet = statmt.executeQuery("SELECT * FROM public.\"Cod\" " +
                "JOIN public.\"Cod_Employee\" using(id_cod) " +
                "join public.\"Employee\" using (id_empl)" +
                "WHERE id_cod ='"+id_cod+"';");
        while (data_resSet.next()){
            String id = data_resSet.getString("id_empl");
            String name = data_resSet.getString("name_empl");
            String post = "Менеджер";
            String age = data_resSet.getString("age_empl");
            String exp = data_resSet.getString("exp_empl");
            String sal = data_resSet.getString("sal_empl");
            Employee employee = new Employee(id,name,post,exp,sal,age);
            arrayList.add(employee);
        }
        return arrayList;
    }
    public static Employee getEmployeebyId(String id_empl) throws SQLException {
        data_resSet = statmt.executeQuery("SELECT *\n" +
                "\tFROM public.\"User\" JOIN public.\"Employee\" using (id_empl) WHERE id_empl = '"+id_empl+"';");
        Employee employee = null;
        while (data_resSet.next()){
            String id = data_resSet.getString("id_empl");
            String name = data_resSet.getString("name_empl");
            String age = data_resSet.getString("age_empl");
            String post = data_resSet.getString("post_empl");
            String exp = data_resSet.getString("exp_empl");
            String login = data_resSet.getString("login_user");
            String passHash = data_resSet.getString("pass_user");
            employee = new Employee(id,name,post,exp,"",age,passHash,login);
        }
        return employee;
    }
    public static ArrayList<Employee> getAllEmployeeMax() throws SQLException {
        ArrayList<Employee> arrayList = new ArrayList<>();
        data_resSet = statmt.executeQuery("SELECT * FROM public.\"Employee\";");
        while (data_resSet.next()){
            String id = data_resSet.getString("id_empl");
            String name = data_resSet.getString("name_empl");
            String post = data_resSet.getString("post_empl");
            String age = data_resSet.getString("age_empl");
            String exp = data_resSet.getString("exp_empl");
            String sal = data_resSet.getString("sal_empl");
            Employee employee = new Employee(id,name,post,exp,sal,age);
            arrayList.add(employee);
        }
        return arrayList;
    }
    public static String  convertPost(String post){
        String answer = "";
        if(post.equals("Менеджер")){
            answer = "CODManager";
        }
        if(post.equals("Аналитик")){
            answer = "CODAnalyst";
        }
        if(post.equals("Инженер")){
            answer = "CODEngineer";
        }
        if(post.equals("Администратор")){
            answer = "CODAdmin";
        }
        return answer;
    }
    public static void updateEmpl (String id,String oldpost, String post ,String name, String exp, String age, String login,
                                   String pass, String zp) throws SQLException {

        statmt.execute("UPDATE public.\"Employee\"\n" +
                "\tSET name_empl='"+name+"', post_empl='"+post+"', exp_empl='"+exp+"', sal_empl='"+zp+"', " +
                "age_empl='"+age+"'\n" +
                "\tWHERE id_empl='"+id+"';");
        data_resSet = statmt.executeQuery("SELECT * FROM public.\"User\" WHERE id_empl = '" + id + "';");
        String oldLogin = "";
        String oldPassHash = "";
        while (data_resSet.next()) {
            oldLogin = data_resSet.getString("login_user");
            oldPassHash = data_resSet.getString("pass_user");

        }
        String newPassHash = Crypto.hash(pass);
        if (!oldLogin.equals(login)) {
            statmt.execute("ALTER ROLE \"" + oldLogin + "\"\n" +
                    "\tRENAME TO \"" + login + "\";");
            statmt.execute("UPDATE public.\"User\"\n" +
                    "\tSET login_user='"+login+"\n" +
                    "\tWHERE id_empl = '"+id+"';");
        }
        if(!oldPassHash.equals(newPassHash)&&!pass.isEmpty()) {
            statmt.execute("ALTER ROLE \"" + login + "\"\n" +
                    "\tPASSWORD '" + pass + "';");
            statmt.execute("UPDATE public.\"User\"\n" +
                    "\tSET pass_user='" + newPassHash + "'\n" +
                    "\tWHERE id_empl = '" + id + "';");
        }
        if(!post.equals(oldpost)) {
            oldpost = convertPost(oldpost);
            post = convertPost(post);

            statmt.execute("REVOKE \"" + oldpost + "\" FROM \"" + login + "\";\n");
            statmt.execute("GRANT \"" + post + "\" TO \"" + login + "\";");
        }
    }
    public static void deleteWork(String id_empl, String id_cod) throws  SQLException {
        System.out.println("OK");
        System.out.println(id_empl);
        System.out.println(id_cod);
        statmt.execute("DELETE FROM public.\"Cod_Employee\" WHERE id_empl ="+id_empl+" and id_cod ="+id_cod+";");
    }
    public static void deleteEmpl(String id_empl) throws SQLException {
        statmt.execute("DELETE FROM public.\"Cod_Employee\"\n" +
                "\tWHERE id_empl = '"+id_empl+"';");
        statmt.execute("DELETE FROM public.\"Receipt\"\n" +
                "\tWHERE id_empl = '"+id_empl+"';");
        statmt.execute("DELETE FROM public.\"Employee\"\n" +
                "\tWHERE id_empl = '"+id_empl+"';");
    }

    public static void makeTMP() throws SQLException {
        statmt.execute("CREATE TEMP TABLE all_clients (\n" +
                "    temp_id SERIAL PRIMARY KEY,\n" +
                "    original_id INT,\n" +
                "    fio VARCHAR(255),\n" +
                "    phone VARCHAR(50),\n" +
                "    email VARCHAR(255),\n" +
                "    bonus INT,\n" +
                "    status VARCHAR(50),\n" +
                "    client_type VARCHAR(20)\n" +
                ");");
        statmt.execute("INSERT INTO all_clients (original_id, fio, phone, email, bonus, status, client_type)\n" +
                "SELECT id_client, name_client, tel_client, mail_client, bonus_client, status_client, 'physical' as client_type\n" +
                "FROM public.\"Client_Individ\";");
        statmt.execute("INSERT INTO all_clients (original_id, fio, phone, email, bonus, status, client_type)\n" +
                "SELECT id_client, orgname_client, tel_client, mail_client, bonus_client, status_client, 'legal' as client_type\n" +
                "FROM public.\"Client_Legal\";");
    }
    public static String getEmployeeNamebyLogin() throws SQLException {
        data_resSet = statmt.executeQuery("SELECT * FROM public.\"User\" " +
                "JOIN public.\"Employee\" using (id_empl) " +
                "WHERE login_user = '"+ Front.login+"';");
        String name ="";
        while (data_resSet.next()){
            name = data_resSet.getString("name_empl");
            String[] mas = name.split(" ");
            char[] name_ = mas[1].toCharArray();
            char[] secname_ = mas[2].toCharArray();
            name = mas[0]+" "+name_[0]+". "+secname_[0]+".";
        }
        return name;
    }

    public static ArrayList<ReceiptServ> getServiceName(String id_cod) throws SQLException {
        ArrayList<ReceiptServ> arrayList = new ArrayList<>();
        data_resSet = statmt.executeQuery("SELECT id_equip, id_cod, name_equip, date_equip, price_equip, status_equip, \"freeSpace_equip\", " +
                "date_start_equip, date_end_equip, value_ssd_equip, cpu_equip, ram_equip\n" +
                "\tFROM public.\"Equipment\" WHERE id_cod ='"+id_cod+"';");
        while (data_resSet.next()){
            String id = data_resSet.getString("id_equip");
            String price = data_resSet.getString("price_equip");
            String name = data_resSet.getString("name_equip");
            String ssd = data_resSet.getString("value_ssd_equip");
            String ram = data_resSet.getString("ram_equip");
            String cpu = data_resSet.getString("cpu_equip");
            String answer = "OS:"+name + " SSD:"+ssd+" RAM:"+ram+" CPU:"+cpu;
            ReceiptServ receiptServ = new ReceiptServ(id,price,answer);
            arrayList.add(receiptServ);
        }
        return arrayList;
    }
    public static void addReceipt(ArrayList<ReceiptServ> receiptServs,String id_client,
                                  String id_empl, String summ, String type) throws SQLException {
        statmt.execute("INSERT INTO public.\"Receipt\"(\n" +
                "\tid_client, id_empl, summ_receipt, type_receipt)\n" +
                "\tVALUES ('"+id_client+"', '"+id_empl+"', '"+summ+"', '"+type+"');");
        String id_receipt = "";
        data_resSet = statmt.executeQuery("SELECT id_receipt, id_client, id_empl, summ_receipt, type_receipt\n" +
                "\tFROM public.\"Receipt\" WHERE id_client = '"+id_client+"' and type_receipt ='"+type+"';");
        while (data_resSet.next()){
            id_receipt = data_resSet.getString("id_receipt");
        }
        if(!id_receipt.isEmpty()){
            for(int i=0;i<receiptServs.size();i++){
                statmt.execute("INSERT INTO public.\"Order\"(\n" +
                        "\tid_receipt, id_equip)\n" +
                        "\tVALUES ('"+id_receipt+"', '"+receiptServs.get(i).id_serv+"');");
            }
        }
    }
    public static String getEmployeeIDbyLogin() throws SQLException {
        String id = "";
        data_resSet = statmt.executeQuery("SELECT id_user, login_user, pass_user, id_empl\n" +
                "\tFROM public.\"User\" WHERE login_user = '"+Front.login+"';");
        while (data_resSet.next()){
            id = data_resSet.getString("id_empl");
        }
        return id;
    }

    public static ArrayList<Receipt> getAllReceipt() throws SQLException {
        data_resSet = statmt.executeQuery("SELECT *\n" +
                "\tFROM public.\"Receipt\" JOIN public.\"Client_Legal\" " +
                "using (id_client) JOIN public.\"Employee\" using (id_empl)" +
                "where type_receipt='Юридическое лицо';");
        ArrayList<Receipt> arrayList = new ArrayList<>();
        while (data_resSet.next()){
            String id_receipt = data_resSet.getString("id_receipt");
            String name = data_resSet.getString("orgname_client");
            String summ = data_resSet.getString("summ_receipt");
            String nameEmpl = data_resSet.getString("name_empl");
            String typeEmpl = data_resSet.getString("type_receipt");
            String id_empl = data_resSet.getString("id_empl");
            Receipt receipt = new Receipt(id_receipt,name,nameEmpl,summ,typeEmpl,id_empl);
            arrayList.add(receipt);
        }
        data_resSet = statmt.executeQuery("SELECT *\n" +
                "\tFROM public.\"Receipt\" \n" +
                "\tJOIN public.\"Client_Individ\" using (id_client) \n" +
                "\tJOIN public.\"Employee\" using (id_empl)\n" +
                "\twhere type_receipt='Физическое лицо';");
        while (data_resSet.next()){
            String id_receipt = data_resSet.getString("id_receipt");
            String name = data_resSet.getString("name_client");
            String summ = data_resSet.getString("summ_receipt");
            String nameEmpl = data_resSet.getString("name_empl");
            String typeEmpl = data_resSet.getString("type_receipt");
            String id_empl = data_resSet.getString("id_empl");
            Receipt receipt = new Receipt(id_receipt,name,nameEmpl,summ,typeEmpl,id_empl);
            arrayList.add(receipt);
        }
        return arrayList;
    }

    public static ArrayList<ReceiptServ> getServicebyIDreceipt(String id_receipt) throws SQLException {
        ArrayList<ReceiptServ> arrayList = new ArrayList<>();
        ArrayList<String> idMas = new ArrayList<>();
        data_resSet = data_statmt.executeQuery("SELECT *\n" +
                "\tFROM public.\"Order\" " +
                "JOIN public.\"Equipment\" using(id_equip)  WHERE id_receipt='"+id_receipt+"';");
        while (data_resSet.next()){
            String id = data_resSet.getString("id_equip");
            String price = data_resSet.getString("price_equip");
            String name = data_resSet.getString("name_equip");
            String ssd = data_resSet.getString("value_ssd_equip");
            String ram = data_resSet.getString("ram_equip");
            String cpu = data_resSet.getString("cpu_equip");
            String answer = "OS:"+name + " SSD:"+ssd+" RAM:"+ram+" CPU:"+cpu;
            ReceiptServ receiptServ = new ReceiptServ(id,price,answer);
            arrayList.add(receiptServ);
        }
        return arrayList;
    }
    public static Receipt getReceiptbyID(String id_receipt) throws SQLException {
        ArrayList<Receipt> arrayList = new ArrayList<>();
        data_resSet = data_statmt.executeQuery("SELECT *\n" +
                "\tFROM public.\"Receipt\" JOIN public.\"Client_Legal\" " +
                "using (id_client) JOIN public.\"Employee\" using (id_empl)" +
                "where type_receipt='Юридическое лицо' and id_receipt ='"+id_receipt+"';");
        while (data_resSet.next()){
            String name = data_resSet.getString("orgname_client");
            String summ = data_resSet.getString("summ_receipt");
            String nameEmpl = data_resSet.getString("name_empl");
            String typeEmpl = data_resSet.getString("type_receipt");
            String id_empl = data_resSet.getString("id_empl");
            Receipt receipt = new Receipt(id_receipt,name,nameEmpl,summ,typeEmpl,id_empl);
            arrayList.add(receipt);
        }
        data_resSet = statmt.executeQuery("SELECT *\n" +
                "\tFROM public.\"Receipt\" \n" +
                "\tJOIN public.\"Client_Individ\" using (id_client) \n" +
                "\tJOIN public.\"Employee\" using (id_empl)\n" +
                "\twhere type_receipt='Физическое лицо' and id_receipt ='"+id_receipt+"';");
        while (data_resSet.next()){
            String name = data_resSet.getString("name_client");
            String summ = data_resSet.getString("summ_receipt");
            String nameEmpl = data_resSet.getString("name_empl");
            String typeEmpl = data_resSet.getString("type_receipt");
            String id_empl = data_resSet.getString("id_empl");
            Receipt receipt = new Receipt(id_receipt,name,nameEmpl,summ,typeEmpl,id_empl);
            arrayList.add(receipt);
        }
        Receipt receipt = arrayList.get(0);
        return receipt;
    }
    public static void deleteReceipt(String id_receipt) throws SQLException {
        statmt.execute("DELETE FROM public.\"Order\"\n" +
                "\tWHERE id_receipt ='"+id_receipt+"';");
        statmt.execute("DELETE FROM public.\"Receipt\"\n" +
                "\tWHERE id_receipt = '"+id_receipt+"';");
    }
    public static void updateReceipt(String id_receipt, String date,String summ, ArrayList<ReceiptServ> arrayList)
            throws SQLException {
        statmt.execute("UPDATE public.\"Receipt\"\n" +
                "\tSET summ_receipt = '"+summ+"'\n" +
                "\tWHERE id_receipt ='"+id_receipt+"'");
        statmt.execute("DELETE FROM public.\"Order\"\n" +
                "\tWHERE id_receipt = '"+id_receipt+"';");
        for(int i=0;i<arrayList.size();i++){
            statmt.execute("INSERT INTO public.\"Order\"(\n" +
                    "\tid_receipt, id_equip)\n" +
                    "\tVALUES ('"+id_receipt+"', '"+arrayList.get(i).id_serv+"');");
        }
    }
}
