import java .io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Supermarket
{
    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);
        boolean login = true;
        String name;
        int price, count;
        ArrayList<Admin> allAdmins = new ArrayList<Admin>();
        ArrayList<Customer> allCustomers = new ArrayList<Customer>();
        ArrayList<Products> allProducts = new ArrayList<Products>();
        ArrayList<Bill> allBills = new ArrayList<Bill>();
        HashMap<String, String> adminLogin = new HashMap<String, String>();
        HashMap<String, String> customerLogin = new HashMap<String, String>();
        HashMap<String, Integer> productsMap = new HashMap<String, Integer>();
        int ch,adminId = 1,customerId = 0,productId = 0,attempt = 0,id;


        allAdmins.add(new Admin("admin", "admin@gmail.com" , "admin" , 1));
        adminLogin.put("admin@gmail.com","admin");
        while(true)
        {
            System.out.print("---------------------------Super Market Billing System ------------------------ \n\n 1.Admin \n 2.User \n\n Enter your choice: ");
            ch = sc.nextInt();
            switch(ch)
            {
                case 1:
                    String email,pass,rpass;
                    login = true;
                    while(login)
                    {
                        System.out.print("Enter your mail id : ");
                        email = sc.next();
                        System.out.print("Enter password : ");
                        pass = sc.next();
                        System.out.println(email+" "+pass);
                        if(adminLogin.containsKey(email) && adminLogin.get(email).compareTo(pass) == 0)
                        {
                            Collections.sort(allAdmins, Comparator.comparing(ad -> ad.getAdminEmail()));
                            id = adminFound(email, allAdmins);
                            String adminCid = allAdmins.get(id).getAdminEmail();
                            System.out.println("-------------------- Welcome -------------------");
                            while(login)
                            {
                                System.out.println("Please Choose your option form the list below : \n");
                                System.out.println("1. Add product \n2. Modify product \n3. Delete product");
                                System.out.println("4. Add admin");
                                System.out.println("5. Add user");
                                System.out.println("6. View all products \n7. Modify customer Credits\n8. Reports\n9. Logout");
                                System.out.print("\n Enter your choice : ");
                                ch = sc.nextInt();
                                switch (ch)
                                {
                                    case 1:
                                        productId++;
                                        System.out.print("Enter the name of the product : ");
                                        name = sc.next();
                                        if(!productsMap.containsKey(name))
                                        {
                                            System.out.print("Enter the price of the product : ");
                                            price = sc.nextInt();
                                            System.out.print("Enter total number of product : ");
                                            count = sc.nextInt();
                                            allProducts.add(new Products(name, price, count, productId));
                                            Collections.sort(allProducts, Comparator.comparing(product -> product.name));
                                            productsMap.put(name,count);
                                        }
                                        else
                                        {
                                            System.out.println("Product already exists!!");
                                        }
                                        break;
                                    case 2:
                                        System.out.print("Enter the product Name to be modified : ");
                                        name = sc.next();
                                        if(productsMap.containsKey(name))
                                        {
                                            id = productFound(name, allProducts);
                                            System.out.println("What do you want to modify : ");
                                            System.out.print("\n 1.name\n 2. price\n3. Number of Products \n\n Enter your choice : ");
                                            ch = sc.nextInt();
                                            if(ch == 1)
                                            {
                                                System.out.print("Enter the modified name : ");
                                                name = sc.next();
                                                allProducts.get(id).modifyProductName(name);
                                            }
                                            else if(ch == 2)
                                            {
                                                System.out.print("Enter the modified price : ");
                                                price  = sc.nextInt();
                                                allProducts.get(id).modifyProductPrice(price);
                                            }
                                            else if(ch == 3)
                                            {
                                                System.out.print("Enter the modified count : ");
                                                count  = sc.nextInt();
                                                allProducts.get(id).modifyProductCount(count);
                                            }
                                            else
                                            {
                                                System.out.println("Invalid input");
                                            }
                                            productsMap.put(allProducts.get(id).getProductName(), allProducts.get(id).getProductCount());
                                        }
                                        else
                                        {
                                            System.out.println("A product of the given name doesn't exist.");
                                        }
                                        break;
                                    
                                    case 3:
                                        System.out.print("Enter the books name to be deleted : ");
                                        name = sc.next();
                                        if(productsMap.containsKey(name))
                                        {
                                            id = productFound(name, allProducts);
                                            allProducts.remove(id);
                                            System.out.println("Product successfully removed.");
                                        }
                                        else
                                        {
                                            System.out.println("Product doesn't exist.");
                                        }
                                        break;
                                    
                                    case 4:
                                        boolean flag = true;
                                        adminId++;
                                        while(flag)
                                        {
                                            while(true)
                                            {
                                                System.out.print("Enter mail id: ");
                                                email = sc.next();
                                                String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
                                                Pattern pattern = Pattern.compile(regex);
                                                Matcher matcher = pattern.matcher(email);
                                                if(!matcher.matches())
                                                {
                                                    System.out.println("Enter a valid mail id");
                                                }
                                                else
                                                {
                                                    break;
                                                }
                                            }
                                            if(!adminLogin.containsKey(email))
                                            {
                                                System.out.print("Enter username : ");
                                                name = sc.next();
                                                while(true)
                                                {
                                                    System.out.print("Enter your password : ");
                                                    String adPass = sc.next();
                                                    System.out.print("Re-enter the password : ");
                                                    rpass = sc.next();
                                                    if(adPass.compareTo(rpass) == 0)
                                                    {
                                                        allAdmins.add(new Admin(name, email, pass,adminId));
                                                        adminLogin.put(email,pass);
                                                        System.out.println("Successfully added admin login details");
                                                        flag = false;
                                                        break;
                                                    }
                                                    else
                                                    {
                                                        System.out.println("Passwords don't match!");
                                                    }
                                                }
                                            }
                                            else
                                            {
                                                System.out.println("Username already present");
                                            }
                                        }
                                        break;
                                    
                                    case 5:
                                        flag = true;
                                        customerId++;
                                        while(flag)
                                        {
                                            while(true)
                                            {
                                                System.out.print("Enter mail id: ");
                                                email = sc.next();
                                                String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
                                                Pattern pattern = Pattern.compile(regex);
                                                Matcher matcher = pattern.matcher(email);
                                                if(!matcher.matches())
                                                {
                                                    System.out.println("Enter a valid mail id");
                                                }
                                                else
                                                {
                                                    break;
                                                }
                                            }
                                            if(!customerLogin.containsKey(email))
                                            {
                                                System.out.print("Enter username : ");
                                                name = sc.next();
                                                while(true)
                                                {
                                                    System.out.print("Enter your password : ");
                                                    pass = sc.next();
                                                    System.out.print("Re-enter the password : ");
                                                    rpass = sc.next();
                                                    if(pass.compareTo(rpass) == 0)
                                                    {
                                                        allCustomers.add(new Customer(name, email, pass, customerId, adminCid));
                                                        customerLogin.put(email,pass);
                                                        System.out.print("Successfully added user login details");
                                                        flag = false;
                                                        break;
                                                    }
                                                    else
                                                    {
                                                        System.out.println("Passwords don't match!");
                                                    }
                                                }
                                            }
                                            else
                                            {
                                                System.out.println("Username already present");
                                            }
                                        }
                                        break;
                                    
                                    case 6:
                                        System.out.print("Enter the way in which you want to view the products \n 1. Sort by name \n 2. sort by quantity \n\n Enter your choice : ");
                                        ch = sc.nextInt();
                                        if(ch == 2)
                                        {
                                            Collections.sort(allProducts, Comparator.comparing(product -> product.getProductCount()));
                                        }
                                        else if(ch != 1)
                                        {
                                            System.out.println("Invalid choice.");
                                            break;
                                        }
                                        for(int i = 0 ; i < allProducts.size() ; i++)
                                        {
                                            System.out.print(allProducts.get(i).getProductId()+". Name: "+allProducts.get(i).getProductName()+" Price: "+allProducts.get(i).getProductPrice()+" Stock: "+allProducts.get(i).getProductCount());
                                            if(i%3 == 0)
                                            {
                                                System.out.println("");
                                            }
                                        }
                                        break;
                                    
                                    case 7:
                                        System.out.print("Enter the customer mail id : ");
                                        email = sc.next();
                                        if(customerLogin.containsKey(email))
                                        {
                                            Collections.sort(allCustomers, Comparator.comparing(cus -> cus.getCustomerEmail()));
                                            id = customerFound(email, allCustomers);
                                            System.out.println(id);
                                            System.out.print("Enter the amount of credits to be credited : ");
                                            price = sc.nextInt();
                                            allCustomers.get(id).addCustomerCredit(price);
                                            System.out.println("Succesfully added "+ price+" credits");
                                        }
                                        break;
                                    case 8:
                                        System.out.print("Enter the report you want to view: \n\n 1. Products with less quantity \n 2. Products that are not bought so far \n 3. Customers who has brought for more value \n 4. List of admins who has made more sale \n\n Enter your choice : ");
                                        ch = sc.nextInt();
                                        switch (ch) {
                                            case 1:
                                                Collections.sort(allProducts, Comparator.comparing(product -> product.getProductName()));
                                                for(int i = 0 ; i < allProducts.size()/2 ; i++)
                                                {
                                                   System.out.print(allProducts.get(i).getProductId()+". Name: "+allProducts.get(i).getProductName()+" Price: "+allProducts.get(i).getProductPrice()+" Stock: "+allProducts.get(i).getProductCount());
                                                    if(i%3 == 0)
                                                    {
                                                        System.out.println("");
                                                    }
                                                }
                                                break;
                                            
                                            case 2:
                                                Collections.sort(allProducts, Comparator.comparing(product -> product.getProductCount()));
                                                for(int i = 0 ; i < allProducts.size() ; i++)
                                                {
                                                    if(allProducts.get(i).getProductTotalSold() == 0)
                                                    {
                                                        System.out.print(allProducts.get(i).getProductId()+". Name: "+allProducts.get(i).getProductName()+" Price: "+allProducts.get(i).getProductPrice()+" Stock: "+allProducts.get(i).getProductCount());
                                                        if(i%3 == 0)
                                                        {
                                                            System.out.println("");
                                                        }
                                                    }
                                                    else
                                                    {
                                                        break;
                                                    }
                                                }
                                                System.out.println("");
                                                break;
                                            case 3:
                                                Collections.sort(allCustomers, Comparator.comparing(cus -> cus.getMoneySpent()));
                                                for(int i = allCustomers.size()-1 ; i >= allCustomers.size()/2 ; i--)
                                                {
                                                    System.out.print(allCustomers.get(i).getCustomerId()+". Name: "+allCustomers.get(i).getCustomerName()+" Email: "+allCustomers.get(i).getCustomerEmail()+" Money spent: "+allCustomers.get(i).getMoneySpent());
                                                    if(i%3 == 0)
                                                    {
                                                        System.out.println("");
                                                    }
                                                }
                                                System.out.println("");
                                                break;
                                            case 4:
                                                Collections.sort(allAdmins, Comparator.comparing(ad -> ad.getAdminNumberOfSlaes()));
                                                for(int i = allAdmins.size()-1 ; i >= allAdmins.size()/2 ; i--)
                                                {
                                                    System.out.print(allAdmins.get(i).getAdminId()+". Name: "+allAdmins.get(i).getAdminName()+" Email: "+allAdmins.get(i).getAdminEmail()+" Number of sales: "+allAdmins.get(i).getAdminNumberOfSlaes());
                                                    if(i%3 == 0)
                                                    {
                                                        System.out.println("");
                                                    }
                                                }
                                                System.out.println("");
                                                break;
                                            default:
                                                System.out.println("invalid choice");
                                                break;
                                        }
                                        break;
                                    case 9:
                                        login = false;
                                        break;
                                    default:
                                        System.out.println("Pick a valid choice");
                                        break;
                                }
                            }
                            break;
                        }
                        else if(attempt == 5)
                        {
                            System.out.println("To manny attempts");
                            break;
                        }
                        else
                        {
                            System.out.println("The email and password doesn't match!");
                            attempt++;
                        }

                    }
                    break;
                case 2:
                    login  = true;
                    while(login)
                    {
                        int cost = 0;
                        ArrayList<Products> temp = new ArrayList<Products>();
                        System.out.print("Enter your mail id : ");
                        email = sc.next();
                        System.out.print("Enter password : ");
                        pass = sc.next();
                        if(customerLogin.containsKey(email) && customerLogin.get(email).compareTo(pass) == 0)
                        {
                            Collections.sort(allCustomers, Comparator.comparing(ad -> ad.getCustomerEmail()));
                            int cid = customerFound(email, allCustomers);
                            System.out.println("-------------------- Welcome -------------------");
                            while(login)
                            {
                                Collections.sort(allProducts, Comparator.comparing(product -> product.getProductName()));
                                for(int i = 0 ; i < allProducts.size() ; i++)
                                {
                                    System.out.print(allProducts.get(i).getProductId()+". Name: "+allProducts.get(i).getProductName()+" Price: "+allProducts.get(i).getProductPrice()+" Stock: "+allProducts.get(i).getProductCount());
                                    if(i%3 == 0)
                                    {
                                        System.out.println("");
                                    }
                                }
                                System.out.print("\n Enter the product name you want to buy : ");
                                name = sc.next();
                                if(productsMap.containsKey(name))
                                {
                                    System.out.print("Enter the number of products you want to buy : ");
                                    count = sc.nextInt();
                                    Collections.sort(allProducts, Comparator.comparing(p -> p.getProductName()));
                                    id = productFound(name, allProducts);
                                    if(count <= productsMap.get(name))
                                    {
                                        if(cost + (count*allProducts.get(id).getProductPrice()) > allCustomers.get(cid).getCustomerCredit())
                                        {
                                            System.out.println("Sorry there isin't enough credit to buy these products.");
                                        }
                                        else
                                        {
                                            cost += (count*allProducts.get(id).getProductPrice());
                                            temp.add( new Products(name, allProducts.get(id).getProductPrice(), count, allProducts.get(id).getProductId()));
                                            if(productsMap.get(name) - count == 0)
                                            {
                                                allProducts.remove(id);
                                                productsMap.remove(name);
                                            }
                                            else
                                            {
                                                allProducts.get(id).modifyProductCount(productsMap.get(name) - count);
                                                productsMap.put(name,productsMap.get(name) - count);
                                            }
                                        }
                                    }
                                    else
                                    {
                                        System.out.println("Only "+ productsMap.get(name) +" are left. Do you want to purchase it all !?(1 = yes/others for no)");
                                        ch = sc.nextInt();
                                        if(ch == 1)
                                        {
                                            if((cost+(productsMap.get(name)*allProducts.get(id).getProductPrice())) > allCustomers.get(cid).getCustomerCredit())
                                            {
                                                System.out.println("Sorry there isin't enough credit to buy these products.");
                                            }
                                            else
                                            {
                                                cost += (productsMap.get(name)*allProducts.get(id).getProductPrice());
                                                temp.add( new Products(name, allProducts.get(id).getProductPrice(), productsMap.get(name), allProducts.get(id).getProductId()));
                                                allProducts.remove(id);
                                                productsMap.remove(name);
                                            }
                                        }
                                    }
                                }
                                else
                                {
                                    System.out.println("Sorry the given product is not available.");
                                }
                                System.out.println("Do you still wish to continue shopping?(yes = 1/ no = other number)");
                                if(sc.nextInt() != 1)
                                {
                                    login  = false;
                                    allCustomers.get(cid).modifyMoneySpent(cost);
                                    cost -= (int)(cost/5000);
                                    int loyaltyScore = (int)(cost/100);
                                    cost = cost - (int)(allCustomers.get(cid).getLoyaltyScore()/50);
                                    allCustomers.get(cid).modifyLoyaltyScore((allCustomers.get(cid).getLoyaltyScore()%50)+loyaltyScore);
                                    allBills.add(new Bill(temp, cost, LocalDate.now()));
                                    Collections.sort(allAdmins, Comparator.comparing(ad -> ad.getAdminId()));
                                    name = allCustomers.get(cid).getAdminId();
                                    id = adminFound(name, allAdmins);
                                    allAdmins.get(id).addNumberOfSales();
                                }
                            }
                        }
                        else
                        {
                            System.out.println("Email and password doesn't match ");
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
    static int productFound(String name,ArrayList<Products> allProducts)
    {
        int left = 0 , right = allProducts.size(),mid;
        while(left < right)
        {
            mid = (left+right)/2;
            if(allProducts.get(mid).getProductName().compareTo(name) == 0)
                return mid;
            if(allProducts.get(mid).getProductName().compareTo(name) > 0)
                right = mid;
            else
                left = mid+1;
        }
        return -1;
    }
    static int customerFound(String name,ArrayList<Customer> allProducts)
    {
        int left = 0 , right = allProducts.size(),mid;
        System.out.println(right);
        while(left < right)
        {
            mid = (left+right)/2;
            if(allProducts.get(mid).getCustomerEmail().compareTo(name) == 0)
                return mid;
            else if(allProducts.get(mid).getCustomerEmail().compareTo(name) > 0)
                right = mid;
            else
                left = mid+1;
        }
        return -1;
    }
    static int adminFound(String name,ArrayList<Admin> allProducts)
    {
        int left = 0 , right = allProducts.size(),mid;
        while(left < right)
        {
            mid = (left+right)/2;
            if(allProducts.get(mid).getAdminEmail().compareTo(name) == 0)
                return mid;
            if(allProducts.get(mid).getAdminEmail().compareTo(name) > 0)
                right = mid;
            else
                left = mid+1;
        }
        return -1;
    }
}

class Customer
{
    String name,email,pass,adminId;
    private int id,credit,moneySpent,loyaltyScore;
    private ArrayList<Bill> history = new ArrayList<Bill>();

    public Customer(String name, String email, String pass, int id, String adminId)
    {
        this.name = name;
        this.adminId = adminId;
        this.email = email;
        this.pass = pass;
        this.id = id;
        this.credit = 1000;
        this.history = null;
        this.moneySpent = 0;
        this.loyaltyScore = 0;
    }

    public int getLoyaltyScore()
    {
        return this.loyaltyScore;
    }

    public void modifyLoyaltyScore(int n)
    {
        this.loyaltyScore = n;
    }
    public String getAdminId()
    {
        return this.adminId;
    }
    public int getCustomerId()
    {
        return this.id;
    }

    public String getCustomerName()
    {
        return this.name;
    }

    public String getCustomerEmail()
    {
        return this.email;
    }

    public int getCustomerCredit()
    {
        return this.credit;
    }

    public int getMoneySpent()
    {
        return this.moneySpent;
    }

    public void addCustomerCredit(int n)
    {
        this.credit += n;
    }

    public void addBillHistory(Bill bill)
    {
        this.history.add(bill);
    }
    public void modifyMoneySpent(int n)
    {
        this.moneySpent += n;
    }
}

class Admin
{
    private int id,numberOfSales;
    private String name,email,password;

    public Admin(String name, String email, String password, int id)
    {
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = id;
        this.numberOfSales = 0;
    }

    public int getAdminId()
    {
        return this.id;
    }

    public String getAdminPassword()
    {
        return this.password;
    }
    public String getAdminName()
    {
        return this.name;
    }

    public String getAdminEmail()
    {
        return this.email;
    }

    public int getAdminNumberOfSlaes()
    {
        return this.numberOfSales;
    }

    public void addNumberOfSales()
    {
        this.numberOfSales++;
    }

}

class Products
{
    private int id,count,price,totalSold;
    String name;

    public Products(String name, int price, int count,int id)
    {
        this.name = name;
        this.price = price;
        this.count = count;
        this.id = id;
        this.totalSold = 0;
    }

    public String getProductName()
    {
        return this.name;
    }

    public int getProductPrice()
    {
        return this.price;
    }

    public int getProductCount()
    {
        return this.count;
    }

    public int getProductId()
    {
        return this.id;
    }

    public int getProductTotalSold()
    {
        return this.totalSold;
    }

    public void modifyProductName(String name)
    {
        this.name = name;
    }

    public void modifyProductPrice(int price)
    {
        this.price = price;
    }

    public void modifyProductCount(int count)
    {
        this.count = count;
    }

    public void productSold(int n)
    {
        this.totalSold += n;
    }

}

class Bill
{
    ArrayList<Products> List = new ArrayList<Products>();
    int cost;
    LocalDate date;

    public Bill(ArrayList<Products> List, int cost, LocalDate date)
    {
        this.List = List;
        this.cost = cost;
        this.date = date;
    }
}
