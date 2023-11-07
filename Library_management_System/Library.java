import java .io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Library 
{
    public static void main(String args[])
    {
        boolean login = true,start = true;
        Scanner sc = new Scanner(System.in);
        int userId = 0 , adminId = 0, bookId = 0;
        String ch;
        ArrayList<Book> allBooks = new ArrayList<Book>();
        ArrayList<Librarian> allLibrarians = new ArrayList<Librarian>();
        ArrayList<Customer> allCustomers = new ArrayList<Customer>();
        HashMap<String, String> adminLogin = new HashMap<String, String>();
        HashMap<String, String> userLogin = new HashMap<String, String>();


        allLibrarians.add(new Librarian("admin", 1, "admin@gmail.com", "admin"));
        adminLogin.put("admin@gmail.com","admin");
        while(start)
        {
            System.out.print("-------------------------------------LIBRARY--------------------------------------\n\n 1.Librarian \n 2.Customer \n 3.Exit \n\n Please enter who you are with the numeric value beside the name: ");
            String n = sc.nextLine();
            switch (n) 
            {
                case "1":
                    String email,pass;
                    while(true)
                    {    
                        System.out.println("-------------------------------------LOGIN--------------------------------------");
                        System.out.print("Enter your email: ");
                        email = sc.nextLine();
                        System.out.print("Enter your password: ");
                        pass = sc.nextLine();
                        if(adminLogin.containsKey(email) && adminLogin.get(email).compareTo(pass) == 0)
                        {
                            System.out.println(email +" "+ pass);
                            break;
                        }
                    }
                    if(adminLogin.get(email).compareTo(pass) == 0)
                    {
                        login = true;
                        while(login)
                        {
                            System.out.print("-------------------------------------WELCOME-------------------------------------- \n\n 1. Add a new book \n 2. Add an old book \n 3. remove a book \n 4. Add an admin user \n 5. add user \n 6. View all books \n 7. Manage fine limit \n 8. Logout\n\n Enter your choice: ");
                            n = sc.nextLine();
                            switch (n)
                            {
                                case "1":
                                    String bookName, autherName, numberb,bcost;
                                    int number;
                                    bookId++;
                                    System.out.print("Enter the name of the book : ");
                                    bookName = sc.nextLine();
                                    System.out.print("Enter the name of the auther : "); 
                                    autherName = sc.nextLine();
                                    System.out.print("Enter the number of books to be added : ");
                                    numberb = sc.nextLine();
                                    number = Integer.parseInt(numberb);
                                    System.out.print("Enter the cost of the book : ");
                                    bcost = sc.nextLine();
                                    allBooks.add(new Book(bookName, autherName, bookId, number, Integer.parseInt(bcost)));
                                    System.out.println("Successfully added the book");
                                    break;
                                
                                case "2":
                                    String numb,bnumb;
                                    int id;
                                    if(allBooks.size() == 0)
                                    {
                                        System.out.println("No old books to be updated!");
                                        break;
                                    }
                                    Collections.sort(allBooks, Comparator.comparing(b -> b.getBookName()));
                                    for(int i = 0 ; i < allBooks.size() ; i++)
                                    {
                                        System.out.println( allBooks.get(i).getBookId()+" : "+ allBooks.get(i).getBookName()+" --->  stock left: "+ allBooks.get(i).getBookCount());
                                    }
                                    System.out.print("Enter the Book name :");
                                    numb = sc.nextLine();
                                    System.out.print("Enter the number of books to be added : ");
                                    bnumb = sc.nextLine();
                                    number = Integer.parseInt(bnumb);
                                    id = bookFound(numb, allBooks);
                                    if(id >= 0)
                                    {
                                        allBooks.get(id).addBookCount(number);
                                        System.out.println("Book Successfully Added.");
                                    }
                                    else
                                    {
                                        System.out.println("Book not present. Please enter a valid book name");
                                    }
                                    break;
                                
                                case "3":
                                    String cnt;
                                    Book temp;
                                    while(true)
                                    {
                                        if(allBooks.size() == 0)
                                        {
                                            System.out.println("No books to be deleted!");
                                            break;
                                        }
                                        for(int i = 0 ; i < allBooks.size() ; i++)
                                        {
                                            System.out.println( allBooks.get(i).getBookId()+" : "+ allBooks.get(i).getBookName()+" --->  stock left: "+ allBooks.get(i).getBookCount());
                                        }
                                        System.out.print("Enter the Book name :");
                                        numb = sc.nextLine();
                                        id = bookFound(numb, allBooks); 
                                        if(id != -1)
                                        {
                                            while(true)
                                            {
                                                System.out.println("Enter the number of books to be removed :");
                                                cnt = sc.nextLine();
                                                int c = Integer.parseInt(cnt);
                                                if( allBooks.get(id).getBookCount() > c)
                                                {
                                                    allBooks.get(id).updateBookCount(c);
                                                    System.out.println("Successfully removed "+ c +" books from "+allBooks.get(id).getBookName());
                                                    break;
                                                }
                                                else if (allBooks.get(id).getBookCount() == c)
                                                {
                                                    temp = allBooks.remove(id);
                                                    System.out.println("Successfully removed every books from "+temp.getBookName());
                                                    break;
                                                }
                                                else
                                                {
                                                    System.out.println("number of books out of range \n");
                                                }
                                            }
                                        }
                                        else
                                        {
                                            System.out.println("Book not present. Enter a valid book name");
                                        }
                                    }
                                    break;
                                
                                case "4":
                                    adminId++;
                                    boolean flag = true;
                                    while(flag)
                                    {
                                        System.out.print("Enter your username: ");
                                        String adName = sc.nextLine();
                                        while(true)
                                        {
                                            System.out.print("Enter email id : ");
                                            email = sc.nextLine();
                                            System.out.println(email);
                                            String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
                                            Pattern pattern = Pattern.compile(regex);
                                            Matcher matcher = pattern.matcher(email);
                                            if(!matcher.matches())
                                            {
                                                System.out.println("Enter a valid email id");
                                            }
                                            else
                                            {
                                                id = adminFound(email, allLibrarians);
                                                if(id != -1)
                                                {
                                                    System.out.println("Email already exists.");
                                                }
                                                else
                                                {
                                                    break;
                                                }
                                            }
                                        }
                                        while(true)
                                        {
                                            System.out.print("Enter your password : ");
                                            String adPass = sc.nextLine();
                                            System.out.print("Re-enter the password : ");
                                            String rpass = sc.nextLine();
                                            if(adPass.compareTo(rpass) == 0)
                                            {
                                                allLibrarians.add(new Librarian(adName, adminId, email, adPass));
                                                adminLogin.put(email, adPass);
                                                System.out.println(adminLogin.get(email));
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
                                    break;
                                
                                case "5":
                                    flag = true;
                                    userId++;
                                    while(flag)
                                    {
                                        System.out.print("Enter your username: ");
                                        String adName = sc.nextLine();
                                        if(!userLogin.containsKey(adName))
                                        {
                                            while(true)
                                            {
                                                System.out.print("Enter Phone number : ");
                                                email = sc.nextLine();
                                                String regex = "^[0-9]{10}$";
                                                Pattern pattern = Pattern.compile(regex);
                                                Matcher matcher = pattern.matcher(email);
                                                if(!matcher.matches())
                                                {
                                                    System.out.println("Enter a valid phone number");
                                                }
                                                else
                                                {
                                                    break;
                                                }
                                            }
                                            while(true)
                                            {
                                                System.out.print("Enter your password : ");
                                                String adPass = sc.nextLine();
                                                System.out.print("Re-enter the password : ");
                                                String rpass = sc.nextLine();
                                                if(adPass.compareTo(rpass) == 0)
                                                {
                                                    allCustomers.add(new Customer(adName, userId, email, adPass));
                                                    userLogin.put(adName, adPass);
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
                                case "6":
                                    System.out.println("1 -> sort books by name \n other numbers -> sort book by count \n\n Enter your choice :");
                                    ch = sc.nextLine();
                                    if(ch.compareTo("1") == 0)
                                    {
                                        Collections.sort(allBooks, Comparator.comparing(book -> book.getBookName()));
                                    }
                                    else
                                    {
                                        Collections.sort(allBooks, Comparator.comparing(book -> book.getBookCount()));
                                    }
                                    for(int i = 0 ; i < allBooks.size() ; i++)
                                    {
                                        System.out.println( allBooks.get(i).getBookId()+" : "+ allBooks.get(i).getBookName()+" --->  stock left: "+ allBooks.get(i).getBookCount());
                                    }
                                    break;
                                
                                case "7":
                                    //fine limit
                                    break;
                                case "8":
                                    login = false;
                                    break;
                
                                default:
                                    System.out.println(" Please enter a valid option number !!");
                                    break;
                            }
                        }
                    }
                    break;
                
                case "2":
                    login = true;
                    int id;
                    ArrayList<String> books = new ArrayList<String>();
                    while(true)
                    {
                        System.out.print("Enter your username : ");
                        email = sc.nextLine();
                        System.out.print("Enter your password :");
                        pass = sc.nextLine();
                        if(userLogin.containsKey(email) && userLogin.get(email).compareTo(pass) == 0)
                        {
                            break;
                        }
                        System.out.println("Username and password doesn't match");
                    }
                    while(login)
                    {
                        if(userLogin.containsKey(email) && userLogin.get(email).compareTo(pass) == 0)
                        {
                            Collections.sort(allCustomers, Comparator.comparing(c -> c.getCustomerName()));
                            int cid = userFound(email, allCustomers);
                            System.out.println("--------------------------------Welcome--------------------------\n\n 1. Borrow a book \n 2. Return a book \n 3. Extend return date\n 4. View Previos fines \n 5. Book lost\n 6. Logout");
                            ch = sc.nextLine();
                            switch(ch)
                            {
                                case "1":
                                    Collections.sort(allBooks, Comparator.comparing(book -> book.getBookName()));
                                    for(int i = 0 ; i < allBooks.size() ; i++)
                                    {
                                        System.out.println( allBooks.get(i).getBookId()+" : "+ allBooks.get(i).getBookName()+" --->  stock left: "+ allBooks.get(i).getBookCount());
                                    }
                                    System.out.print(" How do you want to search your book by : \n\n 1. By book name \n 2. By isbn number \n\n Enter your choice : ");
                                    ch = sc.nextLine();
                                    if(ch.compareTo("1") == 0)
                                    {
                                        System.out.print("Enter the book name : ");
                                        String name = sc.nextLine();
                                        id = bookFound(name, allBooks);
                                    }
                                    else if(ch.compareTo("2") == 0)
                                    {
                                        System.out.print("Enter the isbn number ");
                                        ch = sc.nextLine();
                                        id = bookIdFound(Integer.parseInt(ch), allBooks);
                                    }
                                    else
                                    {
                                        id = -1;
                                    }
                                    if(id != -1)
                                    {
                                        if(!books.contains(allBooks.get(id).getBookName()))
                                        {
                                            allBooks.get(id).updateBookCount(1);
                                            allBooks.get(id).incBookBorrowedNumber();
                                            allCustomers.get(cid).borrowBook(allBooks.get(id));
                                            books.add(allBooks.get(id).getBookName());
                                            System.out.println("Succesfully borrowed the book");
                                            if(allBooks.get(id).getBookCount() == 0)
                                            {
                                                allBooks.remove(id);
                                            }
                                            if(books.size() == 3)
                                            {
                                                System.out.println("borrowed maximum number of books.");
                                                break;
                                            }
                                        }
                                        else
                                        {
                                            System.out.println("Cannot borrow same book twice!");
                                        }
                                    }
                                    else
                                    {
                                        System.out.println("Book not available");
                                    }
                                    break;
                                case "2":
                                    if(allCustomers.get(cid).getNumberOfBooksBorrowed() > 0)
                                    {
                                        ArrayList<Book> bor = new ArrayList<Book>();
                                        bor.addAll(allCustomers.get(cid).getBorrowedBooks());
                                        System.out.println("Enter the name of the book to be returned : ");
                                        String name = sc.nextLine();
                                        id = bookFound(name, bor);
                                        if(id != -1)
                                        {
                                            allCustomers.get(cid).returnBook(bor.get(id));
                                            System.out.println("The book has successfully been returned. And you have to pay a fine amount of "+ allCustomers.get(cid).getFineAmount());
                                        }
                                        else
                                        {
                                            System.out.println("Youe have not borrowed this book.");
                                        }
                                    }
                                    else
                                    {
                                        System.out.println("You've not borrowed any bookd yet!");
                                    }
                                    break;

                                case "3":
                                    if(allCustomers.get(cid).getNumberOfBooksBorrowed() > 0)
                                    {
                                        ArrayList<Book> bor = new ArrayList<Book>();
                                        bor.addAll(allCustomers.get(cid).getBorrowedBooks());
                                        System.out.println("Enter the name of the book to be extended : ");
                                        String name = sc.nextLine();
                                        Collections.sort(bor, Comparator.comparing(b -> b.getBookName()));
                                        id = bookFound(name, bor);
                                        if(id != -1)
                                        {
                                            allCustomers.get(cid).incExtension(bor.get(id));
                                        }
                                        else
                                        {
                                            System.out.println("Youe have not borrowed this book.");
                                        }
                                    }
                                    else
                                    {
                                        System.out.println("You've not borrowed any bookd yet!");
                                    }
                                    break;
                                case "4":
                                    allCustomers.get(cid).fineHistoryList();
                                    break;
                                case "5":
                                    ArrayList<Book> bor = new ArrayList<Book>();
                                    System.out.print("Enter the name of the book that's lost : ");
                                    String name = sc.nextLine();
                                    bor.addAll(allCustomers.get(cid).getBorrowedBooks());
                                    Collections.sort(bor, Comparator.comparing(b -> b.getBookName()));
                                    id = bookFound(name, bor);
                                    if(id != -1)
                                    {
                                        allCustomers.get(cid).lostBook(bor.get(id));
                                    }
                                    break;
                                case "6":
                                    login = false;
                                    break;
                                default:
                                    System.out.println("Enter a valid choice.");
                                    break;
                            }
                        }
                        else
                        {
                            System.out.println("username and passwords don't match");
                        }
                    }
                    break;
                
                case "3":
                    start = false;
                    break;
            }
        }
        sc.close();
    }

    static int bookFound(String name,ArrayList<Book>allBooks)
    {
        int left = 0 , right = allBooks.size(),mid;
        while(left < right)
        {
            mid = (left+right)/2;
            if(allBooks.get(mid).getBookName().compareTo(name) == 0)
                return mid;
            if(allBooks.get(mid).getBookName().compareTo(name) > 0)
                right = mid;
            else
                left = mid+1;
        }
        return -1;
    }
    static int adminFound(String name,ArrayList<Librarian>allBooks)
    {
        int left = 0 , right = allBooks.size(),mid;
        while(left < right)
        {
            mid = (left+right)/2;
            if(allBooks.get(mid).getAdminEmail().compareTo(name) == 0)
                return mid;
            if(allBooks.get(mid).getAdminEmail().compareTo(name) > 0)
                right = mid;
            else
                left = mid+1;
        }
        return -1;
    }

    static int bookIdFound(int id,ArrayList<Book>allBooks)
    {
        int left = 0 , right = allBooks.size(),mid;
        while(left < right)
        {
            mid = (left+right)/2;
            if(allBooks.get(mid).getBookId() == id)
                return mid;
            if(allBooks.get(mid).getBookId() > id)
                right = mid;
            else
                left = mid+1;
        }
        return -1;
    }

    static int userFound(String name,ArrayList<Customer>allBooks)
    {
        int left = 0 , right = allBooks.size(),mid;
        while(left < right)
        {
            mid = (left+right)/2;
            if(allBooks.get(mid).getCustomerName().compareTo(name) == 0)
                return mid;
            if(allBooks.get(mid).getCustomerName().compareTo(name) > 0)
                right = mid;
            else
                left = mid+1;
        }
        return -1;
    }
    
}

class Book
{
    private int id,cost,borrowedNumber;
    int count,extensions;
    private String name;
    private String auther;
    public LocalDate borrowDate , returnedDate;

    public Book(String name, String auther,int id, int count,int cost)
    {
        this.name = name;
        this.cost = cost;
        this.auther = auther;
        this.borrowedNumber = 0;
        this.id = id;
        this.count = count;
        this.borrowDate = null;
        this.returnedDate = null;
        this.extensions = 0;
    }

    public String getBookName()
    {
        return this.name;
    }

    public String getBookAuther()
    {
        return this.auther;
    }

    public int getBookId()
    {
        return this.id;
    }

    public int getBookCount()
    {
        return this.count;
    }

    public void addBookCount(int n)
    {
        this.count += n;
    }

    public int getBookCost()
    {
        return this.cost;
    }

    public int bookBorrowedNumber()
    {
        return this.borrowedNumber;
    }

    public int getExtension()
    {
        return this.extensions;
    }

    public void incExtension()
    {
        this.extensions++;
    }

    public void incBookBorrowedNumber()
    {
        this.borrowedNumber++;
    }

    public void updateBookCount(int n)
    {
        this.count -= n;
    }

}

class Customer
{
    private String userName, password;
    private int userId;
    private String phoneNumber;
    public int fine, amount;
    ArrayList<Book> books = new ArrayList<Book>();
    ArrayList<String> fineHistory = new ArrayList<String>();

    public Customer(String userName, int userId, String phoneNumber, String password)
    {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.fine = 0;
        this.amount = 1500;
        this.phoneNumber = phoneNumber;
    }

    public String getCustomerName()
    {
        return this.userName;
    }
    public void borrowBook(Book bookName)
    {
        Book tempBook = new Book(bookName.getBookName(), bookName.getBookAuther(), bookName.getBookId(),bookName.getBookCount(),bookName.getBookCost());
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plus(2, ChronoUnit.WEEKS);
        tempBook.borrowDate = currentDate;
        tempBook.returnedDate = futureDate;
        this.books.add(tempBook);
    }

    public int returnBook(Book bookName)
    {
        int days,sum = 0;
        for(int i = 0 ; i < this.books.size() ; i++)
        {
            if(this.books.get(i).getBookName().compareTo(bookName.getBookName()) == 0)
            {
                if(this.books.get(i).returnedDate.compareTo(LocalDate.now()) < 0)
                {
                    days = this.books.get(i).returnedDate.until(LocalDate.now()).getDays();
                    if(days <= 15)
                        sum = days*2;
                    else
                        sum = (days*2) + 20;
                    this.fine += sum < (0.8 * this.books.get(i).getBookCost()) ?  sum : (0.8 * this.books.get(i).getBookCost());
                }    
                this.books.remove(i);
                fineHistory.add("Late return");
                fineHistory.add(Integer.toString(sum));
                return 0;
            }
        }
        return -1;
    }

    public void borrowedBooks()
    {
        for(int i = 0 ; i < books.size() ; i++)
        {
            System.out.println(books.get(i).getBookId() + " " + books.get(i).getBookName());
        }
    }

    public void incExtension(Book ebook)
    {
        for(int i = 0 ; i < this.books.size() ; i++)
        {
            if(this.books.get(i).getBookName().compareTo(ebook.getBookName()) == 0)
            {
                if(this.books.get(i).getExtension() > 2)
                {
                    System.out.println("Exceeded Extend limit");
                }
                else
                {
                    this.books.get(i).incExtension();
                    this.books.get(i).returnedDate.plus(1, ChronoUnit.WEEKS);
                    System.out.println("Succesfully extended.");
                }
                break;
            }
        }
    }

    public ArrayList<Book> getBorrowedBooks()
    {
        return this.books;
    }
    public int getNumberOfBooksBorrowed()
    {
        return this.books.size();
    }

    public int getFineAmount()
    {
        return this.fine;
    }
    public void lostBook(Book book)
    {
        this.fine += (book.getBookCost()/2);
        System.out.println("THe total amount you have to pay as fine is : "+ this.fine);
        fineHistory.add("Book Lost");
        fineHistory.add(Integer.toString((int)(book.getBookCost()/2)));

    }
    public void fineHistoryList()
    {
        for(int i = 0; i < this.fineHistory.size() ; i+=2)
        {
            System.out.println(this.fineHistory.get(i)+" "+ this.fineHistory.get(i+1));
        }
    }

}

class Librarian
{
    private String userName, password;
    private String email;
    private int userId;

    public Librarian(String userName, int userId, String email, String password)
    {
        this.userName = userName;
        this.userId = userId;
        this.password = password;
        this.email = email;
    }

    public String getAdminEmail()
    {
        return this.email;
    }

}
