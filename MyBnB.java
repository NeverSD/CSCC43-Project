import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MyBnB {

	private static final String dbClassName = "com.mysql.cj.jdbc.Driver";
	private static final String CONNECTION = "jdbc:mysql://localhost:3307/mydb";
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws ClassNotFoundException {
		Class.forName(dbClassName);
		final String USER = "root";
		final String PASS = "l0008005";
		System.out.println("Connecting to database...");
		
		try {
			//Establish connection
			Connection conn = DriverManager.getConnection(CONNECTION,USER,PASS);
			System.out.println("Successfully connected!");
			//Log in and Sign up
			int finish = 0;
			int operate = 0;
			int account_number = 0;
			String str = "";
			String name = "";
			String sql = "";
			ResultSet rs = null;
			Statement stmt = null;
			while(finish == 0) {
				System.out.println("------------------------");
				System.out.println("What do you want to do?");
				System.out.println("1.Login");
				System.out.println("2.Signup");
				System.out.println("0.admin");
				@SuppressWarnings("resource")
				Scanner input = new Scanner(System.in);
				operate=input.nextInt();
				if(operate == 1) {
					System.out.println("------------------------");
					System.out.println("Enter your Account Number:");
					input = new Scanner(System.in);
					account_number =input.nextInt();
					System.out.println("Enter your password:");
					input = new Scanner(System.in);
					str = input.next();
					sql = "SELECT user_name,user_type FROM Users "
							+ "WHERE user_account_number=\"" + account_number
							+"\" AND user_password=\"" + str
							+ "\";";
					//System.out.println(sql);//
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql);
					while(rs.next()){
						name = rs.getString("user_name");
						finish = rs.getInt("user_type");
					}
					if(finish == 0){
						System.out.println("Account or Password Error!");
					}
				}else if (operate == 2) {
					System.out.println("------------------------");
					System.out.println("Create your Account Number:");
					input = new Scanner(System.in);
					account_number =input.nextInt();
					System.out.println("Create your password:");
					input = new Scanner(System.in);
					String pw = input.next();
					System.out.println("Sign up as a:");
					System.out.println("1.host");
					System.out.println("2.renter");
					input = new Scanner(System.in);
					operate=input.nextInt();
					System.out.println("Enter your name:");
					input = new Scanner(System.in);
					name = input.nextLine();
					System.out.println("Enter your social insurance number:");
					input = new Scanner(System.in);
					int SIN =input.nextInt();
					System.out.println("Enter your address:");
					input = new Scanner(System.in);
					String address = input.nextLine();
					System.out.println("Year of birth[YYYY]:");
					input = new Scanner(System.in);
					int year =input.nextInt();
					while(2023 - year < 18) {
						System.out.println("Please enter a vaild year[YYYY]");
						input = new Scanner(System.in);
						year =input.nextInt();
					}
					System.out.println("Month of birth[MM]:");
					input = new Scanner(System.in);
					String month =input.next();
					System.out.println("Day of birth[DD]:");
					input = new Scanner(System.in);
					String day =input.next();
					System.out.println("What's your occupation:");
					input = new Scanner(System.in);
					String occupation = input.nextLine();
					System.out.println("Enter your payment information:");
					input = new Scanner(System.in);
					String payment_info = input.nextLine();
					sql = "INSERT INTO Users(user_account_number,user_password,user_name,SIN,user_type,user_address,date_of_birth,occupation,payment_info) "
							+ "VALUES (\""+ account_number + "\",\"" + pw + "\",\""+ name + "\",\""+ SIN + "\",\"" + operate + "\",\"" + address + "\",\"" + year + "-" + month + "-" + day + "\",\"" + occupation +"\",\""+ payment_info 
							+ "\");";
					//System.out.println(sql);//
					stmt = conn.createStatement();
					stmt.executeUpdate(sql);
					System.out.println("Account Created!");
					finish = operate;
				}else if (operate == 0) {
					finish = 99;
				}
			}
			
			//host
			while(finish == 1) {				
				System.out.println("------------------------");
				System.out.println("Hello! "+ name +"! You are logged in as a host!");
				System.out.println("What would you like to do today?");
				System.out.println("1.Manage Shelters");
				System.out.println("2.View Comments");
				System.out.println("3.Exit");
				System.out.println("0.Delete Account");
				@SuppressWarnings("resource")
				Scanner input = new Scanner(System.in);
				operate=input.nextInt();
				
				if(operate == 1) {//manage shelters
					System.out.println("------------------------");
					ArrayList<String> list = new ArrayList<String>();
					sql = "SELECT s_name,property_type FROM Shelters WHERE s_host =\""+ account_number + "\";";
					//System.out.println(sql);//
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql);
					int i = 0;
					while(rs.next()&&i<9){
						list.add(rs.getString("s_name"));
						int type= rs.getInt("property_type");
						String property_type = "ERROR";
						if(type == 1){property_type="House";}
						if(type == 2){property_type="Apartment";}
						if(type == 3){property_type="Guesthouse";}
						if(type == 4){property_type="Hotel";}
						System.out.println((i+1) + "."+list.get(i)+" ["+ property_type +"]");
						i = i+1;
					}
					System.out.println("0.Registe a new shelter");
					input = new Scanner(System.in);
					operate=input.nextInt() - 1;
					if(operate == -1 && i<9) {//new shelter
						System.out.println("------------------------");
						System.out.println("Name of the New Shelter:");
						input = new Scanner(System.in);
						String s_name =input.nextLine();
						System.out.println("What property type is this shelter?");
						System.out.println("1.House");
						System.out.println("2.Aparment");
						System.out.println("3.Gusthouse");
						System.out.println("4.Hotel");
						input = new Scanner(System.in);
						int type = input.nextInt();
						System.out.println("Longitude:");
						input = new Scanner(System.in);
						float longitude = input.nextFloat();
						System.out.println("Latitude:");
						input = new Scanner(System.in);
						float latitude = input.nextFloat();
						System.out.println("Enter Country of your shelter:");
						input = new Scanner(System.in);
						String country =input.nextLine();
						System.out.println("Enter the city of the shelter:");
						input = new Scanner(System.in);
						String city =input.nextLine();
						System.out.println("Enter the Detailed address of the shelter:");
						input = new Scanner(System.in);
						String address =input.nextLine();
						System.out.println("Enter the zip-code of the shelter:");
						input = new Scanner(System.in);
						String zip_code =input.nextLine();
						System.out.println("Describe the characteristic of the shelter:");
						input = new Scanner(System.in);
						String characteristic =input.nextLine();
						System.out.println("Set a price for the Shelter:");
						input = new Scanner(System.in);
						float price =input.nextFloat();
						sql = "INSERT INTO Shelters(s_name,s_host,property_type,longitude,latitude,s_address,characteristic,price,country,city,zip_code) "
								+"SELECT \""+s_name+"\",user_account_number,\""+type+"\",\""+longitude+"\",\""+latitude+"\",\""+address+"\",\""+characteristic+"\", \""+ price + "\",\""+country+"\",\""+city+"\",\""+zip_code+"\" "
								+"FROM Users "
								+"WHERE user_account_number = \""+account_number+ "\";";
						System.out.println(sql);//
						stmt = conn.createStatement();
						stmt.executeUpdate(sql);
						System.out.println("Shelter Registered!");
					}else if(operate == -1 && i >= 9){
						System.out.println("You have reach the max number of shelters registered!");
						
					}else if(operate < i){//select shelter
						String s_name = list.get(operate);
						System.out.println("------------------------");
						System.out.println("What would you like to do to "+s_name +"?");
						System.out.println("1.Check information");
						System.out.println("2.Check activity");
						System.out.println("0.Delete");
						input = new Scanner(System.in);
						operate=input.nextInt();
						if(operate == 1) {//info
							sql = "SELECT * FROM Shelters WHERE s_host =\""+ account_number + "\" AND s_name = \"" + s_name +"\";";
							//System.out.println(sql);//
							stmt = conn.createStatement();
							rs = stmt.executeQuery(sql);
							while(rs.next()){
								int type= rs.getInt("property_type");
								String property_type = "ERROR";
								if(type == 1){property_type="House";}
								if(type == 2){property_type="Apartment";}
								if(type == 3){property_type="Guesthouse";}
								if(type == 4){property_type="Hotel";}
								//System.out.println("11111111111");//
								float longitude = rs.getFloat("longitude");
								//System.out.println("11111111111");//
								float latitude = rs.getFloat("latitude");
								//System.out.println("11111111111");//
								String country = rs.getString("country");
								String city = rs.getString("city");
								String zip_code = rs.getString("zip_code");
								String address = rs.getString("s_address");
								String characteristic = rs.getString("characteristic");
								float price = rs.getFloat("price");
								System.out.println("Name: " + s_name);
								System.out.println("Property Type: " + property_type);
								System.out.println("Longitude: " + longitude);
								System.out.println("Latitude: " + latitude);
								System.out.println("Country: " + country);
								System.out.println("City: " + city);
								System.out.println("Address: " + address);
								System.out.println("Zip_code: " + zip_code);
								System.out.println("Characteristic: " + characteristic);
								System.out.println("Price: $" + price);
							}
							System.out.println("1.Confirm");
							System.out.println("2.Change Price");
							input = new Scanner(System.in);
							operate=input.nextInt();
							if(operate == 2){//Change price
								System.out.println("------------------------");
								System.out.println("Connecting...");
								sql = "SELECT * From Activity  "
										+ "WHERE a_host = \""+ account_number +"\" AND a_shelter =\""+ s_name+"\" AND a_status = \"0\";";
								//System.out.println(sql);//
								stmt = conn.createStatement();
								rs = stmt.executeQuery(sql);
								i=0;
								while(rs.next()){
									System.out.println("222222222222222");
									i = i+1;
								}
								if(i > 0) {
									System.out.println("There exist activities on going! Please set the status or remove it, then change the price.");
								}else {
									System.out.println("Enter a new price");
									input = new Scanner(System.in);
									float new_price =input.nextFloat();
									System.out.println("------------------------");
									sql="UPDATE Shelters "
											+ "SET price = \""+new_price+"\" "
											+ "WHERE s_name = \""+s_name+"\" AND s_host = \""+account_number+"\";";
									stmt.executeUpdate(sql);
									System.out.println("Price has been updated successfully!");
									System.out.println("1.Confirm");
									input = new Scanner(System.in);
									operate=input.nextInt();
								}
							}
							
						}else if(operate == 2){//check activity
							System.out.println("------------------------");
							sql = "SELECT * From Activity  "
									+ "WHERE a_host = \""+ account_number +"\" AND a_shelter =\""+ s_name+"\";";
							//System.out.println(sql);//
							stmt = conn.createStatement();
							rs = stmt.executeQuery(sql);
							ArrayList<String> date_lst = new ArrayList<String>();
							ArrayList<String> status_lst = new ArrayList<String>();
							ArrayList<Integer> renter_lst = new ArrayList<Integer>();
							i = 0;
							while(rs.next()&&i<9){
								date_lst.add(rs.getString("a_date"));
								int stat= rs.getInt("a_status");
								if(stat == 0){status_lst.add("On going");}
								if(stat == 1){status_lst.add("Done");}
								renter_lst.add(rs.getInt("a_renter"));
								System.out.println((i+1) + ".["+date_lst.get(i)+"] "+ status_lst.get(i));
								i = i+1;
								}
								System.out.println("0.Cancel");
								if(i == 9) {System.out.println("Your Activity History is full! Please delete Unnecessary ones to view more!");} 
								input = new Scanner(System.in);
								operate=input.nextInt()-1;
								if(operate < i && operate >=0){//activity
									System.out.println("------------------------");
									String date = date_lst.get(operate);
									String status = status_lst .get(operate);
									int renter = renter_lst.get(operate);
									System.out.println("SELECTED:["+date+"] "+ s_name + " ["+status+"]");
									if(status == "On going") {System.out.println("1.Change Status");}
									if(status == "Done") {System.out.println("1.Comment");}
									System.out.println("2.Remove");
									System.out.println("0.Cancel");
									input = new Scanner(System.in);
									operate=input.nextInt();
									if(operate == 1 && status == "On going") {//change status
										sql = "UPDATE Activity "
												+ "SET a_status = \"1\" "
												+ "WHERE a_host = \""+account_number+"\" AND a_shelter = \""+s_name+"\";";
										System.out.println("------------------------");
										stmt.executeUpdate(sql);
										System.out.println("Status changed Successfully!");
										System.out.println("1.Confirm");
										input = new Scanner(System.in);
										operate=input.nextInt();
									}else if(operate == 1 && status == "Done") {//comment
										System.out.println("------------------------");
										System.out.println("Comment on your renter!");
										input = new Scanner(System.in);
										String content = input.nextLine();
										System.out.println("Rate on Scale 1-5!");
										input = new Scanner(System.in);
										int rate = input.nextInt();
										while(rate<1 || rate > 5) {
											System.out.println("Please Rate on Scale 1-5! Other integers will not be approved!");
											input = new Scanner(System.in);
											rate = input.nextInt();
										}
										System.out.println("Connecting...");
										System.out.println("------------------------");
										sql = "INSERT INTO Comments(content, rate, commented,poster) "
												+ "VALUES (\""+content+"\",\""+rate+"\",\""+renter+"\",\""+account_number+"\");";
										stmt.executeUpdate(sql);
										System.out.println("Commented Successfully! Thank you!");
										System.out.println("1.Confirm");
										input = new Scanner(System.in);
										operate=input.nextInt();
									}else if(operate == 2) {
										sql = "DELETE FROM Activity "
												+ "WHERE a_host = \""+account_number+"\" AND a_shelter = \""+s_name+"\";";
										stmt.executeUpdate(sql);
										System.out.println("Activity Removed!");
									}
								}
							
							
						}else if(operate == 0) {//delete shelter
							System.out.println("------------------------");
							System.out.println("Are you sure you want to delete the shelter?");
							System.out.println("1.Confirm");
							System.out.println("2.Cancel");
							input = new Scanner(System.in);
							operate=input.nextInt();
							if(operate == 1) {
								sql = "DELETE FROM Shelters WHERE s_host =\""+ account_number + "\" AND s_name = \"" + s_name +"\";";
								stmt.executeUpdate(sql);
								sql = "DELETE FROM Activity WHERE a_host =\""+ account_number + "\" AND a_shelter = \"" + s_name +"\";";
								stmt.executeUpdate(sql);
								System.out.println("Shelter Deleted!");
							}
						}
					
					}	
				}else if(operate == 2) {//View Comments
					System.out.println("------------------------");
					sql = "SELECT * From Comments "
							+ "WHERE commented = \""+account_number+"\";";
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql);
					while(rs.next()){
						String content = rs.getString("content");
						int rate = rs.getInt("rate");
						System.out.println(content);
						System.out.println("Rating:" + rate);
						System.out.println("------------------------");
					}
					System.out.println("1.Confirm");
					input = new Scanner(System.in);
					operate=input.nextInt();
					
					
				}else if(operate == 3) {
					finish = 0;
				}else if(operate == 0) {//delete
					System.out.println("------------------------");
					System.out.println("Are you sure you want to delete the account?");
					System.out.println("1.Confirm");
					System.out.println("2.Cancel");
					input = new Scanner(System.in);
					operate=input.nextInt();
					if(operate == 1) {
						sql = "DELETE FROM Users WHERE user_account_number =\""+ account_number + "\";";
						stmt.executeUpdate(sql);
						sql = "DELETE FROM Shelters WHERE s_host =\""+ account_number + "\";";
						stmt.executeUpdate(sql);
						sql = "DELETE FROM Activity WHERE a_host =\""+ account_number +"\";";
						stmt.executeUpdate(sql);
						System.out.println("Account Deleted!");
						finish = 0;
					}
				}
			}
			
			//renter
			while(finish == 2) {
				System.out.println("------------------------");
				System.out.println("Hello! "+ name +"! You are logged in as a renter!");
				System.out.println("What would you like to do today?");
				System.out.println("1.Book a shelter");
				System.out.println("2.View Activity");
				System.out.println("3.View Comment");
				System.out.println("4.Exit");
				System.out.println("0.Delete Account");
				@SuppressWarnings("resource")
				Scanner input = new Scanner(System.in);
				operate=input.nextInt();
				
				if(operate == 1) {//book
					System.out.println("------------------------");
					System.out.println("1.Search by geographical location");
					System.out.println("2.Search in city");
					System.out.println("3.Search by zip-code");
					input = new Scanner(System.in);
					operate=input.nextInt();
					if(operate == 1) {//by geo
						System.out.println("------------------------");
						System.out.println("Enter the geographical location you will be in");
						System.out.println("Longtitude:");
						input = new Scanner(System.in);
						float longitude = input.nextFloat();
						System.out.println("Latitude:");
						input = new Scanner(System.in);
						float latitude = input.nextFloat();
						System.out.println("Enter the date you need");
						System.out.println("Year [YYYY]:");
						input = new Scanner(System.in);
						String year =input.next();
						System.out.println("Month [MM]:");
						input = new Scanner(System.in);
						String month =input.next();
						System.out.println("Day [DD]:");
						input = new Scanner(System.in);
						String day =input.next();
						String date = year+"-"+month+"-"+day;
						System.out.println("Searching...");
						sql = "SELECT * FROM Shelters "
								+ "WHERE ((longitude<\""+(longitude+1)+"\" AND longitude >\""+ (longitude-1) +"\") OR (longitude < \""+(longitude+361)+"\" AND longitude > \""+(longitude+359)+"\") OR (longitude < \""+(longitude-359)+"\" AND longitude > \""+(longitude-361)+"\")) "
								+ "AND latitude < \""+(latitude +1)+"\" AND latitude > \""+(latitude -1)+"\" "
								+ "AND NOT EXISTS "
								+ "(SELECT s_name,s_host,property_type,longitude,latitude,s_address,characteristic,price,country,city "
								+ "FROM Shelters, Activity "
								+ "WHERE s_name = a_shelter AND s_host = a_host AND a_date = \""+date+"\");";
						//System.out.println(sql);//
						stmt = conn.createStatement();
						rs = stmt.executeQuery(sql);
						System.out.println("------------------------");
						ArrayList<String> s_name_lst = new ArrayList<String>();
						ArrayList<Integer> host_lst = new ArrayList<Integer>();
						ArrayList<String> type_lst = new ArrayList<String>();
						ArrayList<String> address_lst = new ArrayList<String>();
						int i = 0;
						while(rs.next()&&i<9){
							s_name_lst.add(rs.getString("s_name"));
							host_lst.add(rs.getInt("s_host"));
							int type= rs.getInt("property_type");
							if(type == 1){type_lst.add("House");}
							if(type == 2){type_lst.add("Apartment");}
							if(type == 3){type_lst.add("Guesthouse");}
							if(type == 4){type_lst.add("Hotel");}
							address_lst.add(rs.getString("s_address"));
							float price = rs.getFloat("price");
							System.out.println((i+1) + "."+s_name_lst.get(i)+" ["+ type_lst.get(i) +"]: "+address_lst.get(i) +"     $" + price);
							i = i+1;
							}
						System.out.println("0.Cancel");
						input = new Scanner(System.in);
						operate=input.nextInt()-1;
						if(operate < i && operate >=0){//book
							System.out.println("------------------------");
							sql = "INSERT INTO Activity(a_date,a_host,a_shelter,a_renter,a_status) "
									+ "SELECT \""+date+"\",s_host,s_name,\""+account_number+"\",0 "
									+ "FROM Shelters "
									+ "WHERE s_host = \""+host_lst.get(operate)+"\" AND s_name = \""+s_name_lst.get(operate)+"\";";
							System.out.println("Booked Successfully! You can check it in Activity!");
							System.out.println("1. Confirm");
							input = new Scanner(System.in);
							operate=input.nextInt();
						}
					}else if(operate == 2) {//by city
						System.out.println("------------------------");
						System.out.println("Enter the Country you will be in");
						input = new Scanner(System.in);
						String country = input.nextLine();
						System.out.println("Enter the City you will be in");
						input = new Scanner(System.in);
						String city = input.nextLine();
						System.out.println("Enter the date you need");
						System.out.println("Year [YYYY]:");
						input = new Scanner(System.in);
						String year =input.next();
						System.out.println("Month [MM]:");
						input = new Scanner(System.in);
						String month =input.next();
						System.out.println("Day [DD]:");
						input = new Scanner(System.in);
						String day =input.next();
						String date = year+"-"+month+"-"+day;
						System.out.println("Searching...");
						sql = "SELECT * FROM Shelters "
								+ "WHERE country = \""+country+"\" AND city = \""+city+"\" "
								+ "AND NOT EXISTS "
								+ "(SELECT s_name,s_host,property_type,longitude,latitude,s_address,characteristic,price,country,city "
								+ "FROM Shelters, Activity "
								+ "WHERE s_name = a_shelter AND s_host = a_host AND a_date = \""+date+"\");";
						//System.out.println(sql);//
						stmt = conn.createStatement();
						rs = stmt.executeQuery(sql);
						System.out.println("------------------------");
						ArrayList<String> s_name_lst = new ArrayList<String>();
						ArrayList<Integer> host_lst = new ArrayList<Integer>();
						ArrayList<String> type_lst = new ArrayList<String>();
						ArrayList<String> address_lst = new ArrayList<String>();
						int i = 0;
						while(rs.next()&&i<9){
							s_name_lst.add(rs.getString("s_name"));
							host_lst.add(rs.getInt("s_host"));
							int type= rs.getInt("property_type");
							if(type == 1){type_lst.add("House");}
							if(type == 2){type_lst.add("Apartment");}
							if(type == 3){type_lst.add("Guesthouse");}
							if(type == 4){type_lst.add("Hotel");}
							address_lst.add(rs.getString("s_address"));
							float price = rs.getFloat("price");
							System.out.println((i+1) + "."+s_name_lst.get(i)+" ["+ type_lst.get(i) +"]: "+address_lst.get(i) +"     $" + price);
							i = i+1;
							}
						System.out.println("0.Cancel");
						input = new Scanner(System.in);
						operate=input.nextInt()-1;
						if(operate < i && operate >=0){//book
							System.out.println("------------------------");
							sql = "INSERT INTO Activity(a_date,a_host,a_shelter,a_renter,a_status) "
									+ "SELECT \""+date+"\",s_host,s_name,\""+account_number+"\",0 "
									+ "FROM Shelters "
									+ "WHERE s_host = \""+host_lst.get(operate)+"\" AND s_name = \""+s_name_lst.get(operate)+"\";";
							System.out.println("Booked Successfully! You can check it in Activity!");
							System.out.println("1. Confirm");
							input = new Scanner(System.in);
							operate=input.nextInt();
						}
					}else if(operate == 3) {//zip
						System.out.println("------------------------");
						System.out.println("Enter the zip_code");
						input = new Scanner(System.in);
						String zip_code = input.nextLine();
						System.out.println("Enter the date you need");
						System.out.println("Year [YYYY]:");
						input = new Scanner(System.in);
						String year =input.next();
						System.out.println("Month [MM]:");
						input = new Scanner(System.in);
						String month =input.next();
						System.out.println("Day [DD]:");
						input = new Scanner(System.in);
						String day =input.next();
						String date = year+"-"+month+"-"+day;
						System.out.println("Searching...");
						sql = "SELECT * FROM Shelters "
								+ "WHERE zip_code = \""+zip_code+"\" "
								+ "AND NOT EXISTS "
								+ "(SELECT s_name,s_host,property_type,longitude,latitude,s_address,characteristic,price,country,city "
								+ "FROM Shelters, Activity "
								+ "WHERE s_name = a_shelter AND s_host = a_host AND a_date = \""+date+"\");";
						//System.out.println(sql);//
						stmt = conn.createStatement();
						rs = stmt.executeQuery(sql);
						System.out.println("------------------------");
						ArrayList<String> s_name_lst = new ArrayList<String>();
						ArrayList<Integer> host_lst = new ArrayList<Integer>();
						ArrayList<String> type_lst = new ArrayList<String>();
						ArrayList<String> address_lst = new ArrayList<String>();
						int i = 0;
						while(rs.next()&&i<9){
							s_name_lst.add(rs.getString("s_name"));
							host_lst.add(rs.getInt("s_host"));
							int type= rs.getInt("property_type");
							if(type == 1){type_lst.add("House");}
							if(type == 2){type_lst.add("Apartment");}
							if(type == 3){type_lst.add("Guesthouse");}
							if(type == 4){type_lst.add("Hotel");}
							address_lst.add(rs.getString("s_address"));
							float price = rs.getFloat("price");
							System.out.println((i+1) + "."+s_name_lst.get(i)+" ["+ type_lst.get(i) +"]: "+address_lst.get(i) +"     $" + price);
							i = i+1;
							}
						System.out.println("0.Cancel");
						input = new Scanner(System.in);
						operate=input.nextInt()-1;
						if(operate < i && operate >=0){//book
							System.out.println("------------------------");
							sql = "INSERT INTO Activity(a_date,a_host,a_shelter,a_renter,a_status) "
									+ "SELECT \""+date+"\",s_host,s_name,\""+account_number+"\",0 "
									+ "FROM Shelters "
									+ "WHERE s_host = \""+host_lst.get(operate)+"\" AND s_name = \""+s_name_lst.get(operate)+"\";";
							System.out.println("Booked Successfully! You can check it in Activity!");
							System.out.println("1. Confirm");
							input = new Scanner(System.in);
							operate=input.nextInt();
						}
					}
					
					
				}else if(operate == 2) {//Activity
					System.out.println("------------------------");
					sql = "SELECT * From Activity  "
							+ "WHERE a_renter = \""+ account_number +"\";";
					//System.out.println(sql);//
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql);
					ArrayList<String> date_lst = new ArrayList<String>();
					ArrayList<String> s_name_lst = new ArrayList<String>();
					ArrayList<String> status_lst = new ArrayList<String>();
					ArrayList<Integer> host_lst = new ArrayList<Integer>();
					int i = 0;
					while(rs.next()&&i<9){
						date_lst.add(rs.getString("a_date"));
						s_name_lst.add(rs.getString("a_shelter"));
						int stat= rs.getInt("a_status");
						if(stat == 0){status_lst.add("On going");}
						if(stat == 1){status_lst.add("Done");}
						host_lst.add(rs.getInt("a_host"));
						System.out.println((i+1) + ".["+date_lst.get(i)+"] "+ s_name_lst.get(i) +" ["+ status_lst.get(i) +"]");
						i = i+1;
						}
						System.out.println("0.Cancel");
						if(i == 9) {System.out.println("Your Activity History is full! Please delete Unnecessary ones to view more!");} 
						input = new Scanner(System.in);
						operate=input.nextInt()-1;
						if(operate < i && operate >=0){//activity
							System.out.println("------------------------");
							String date = date_lst.get(operate);
							String status = status_lst .get(operate);
							int host = host_lst.get(operate);
							String s_name = s_name_lst.get(operate);
							System.out.println("SELECTED:["+date+"] "+ s_name + " ["+status+"]");
							if(status == "On going") {System.out.println("1.Change Status");}
							if(status == "Done") {System.out.println("1.Comment");}
							System.out.println("2.Remove");
							System.out.println("0.Cancel");
							input = new Scanner(System.in);
							operate=input.nextInt();
							if(operate == 1 && status == "On going") {//change status
								sql = "UPDATE Activity "
										+ "SET a_status = \"1\" "
										+ "WHERE a_host = \""+host+"\" AND a_shelter = \""+s_name+"\";";
								System.out.println("------------------------");
								stmt.executeUpdate(sql);
								System.out.println("Status changed Successfully!");
								System.out.println("1.Confirm");
								input = new Scanner(System.in);
								operate=input.nextInt();
							}else if(operate == 1 && status == "Done") {//comment
								System.out.println("------------------------");
								System.out.println("Comment on your renter!");
								input = new Scanner(System.in);
								String content = input.nextLine();
								System.out.println("Rate on Scale 1-5!");
								input = new Scanner(System.in);
								int rate = input.nextInt();
								while(rate<1 || rate > 5) {
									System.out.println("Please Rate on Scale 1-5! Other integers will not be approved!");
									input = new Scanner(System.in);
									rate = input.nextInt();
								}
								System.out.println("Connecting...");
								System.out.println("------------------------");
								sql = "INSERT INTO Comments(content, rate, commented,poster) "
										+ "VALUE (\""+content+"\",\""+rate+"\",\""+host+"\",\""+account_number+"\");";
								stmt.executeUpdate(sql);
								System.out.println("Commented Successfully! Thank you!");
								System.out.println("1.Confirm");
								input = new Scanner(System.in);
								operate=input.nextInt();
								
							}else if(operate == 2) {
								sql = "DELETE FROM Activity "
										+ "WHERE a_host = \""+host+"\" AND a_shelter = \""+s_name+"\";";
								stmt.executeUpdate(sql);
								System.out.println("Activity Removed!");
							}
						}
				}else if(operate == 3) {//comment
					System.out.println("------------------------");
					sql = "SELECT * From Comments "
							+ "WHERE commented = \""+account_number+"\";";
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql);
					while(rs.next()){
						String content = rs.getString("content");
						int rate = rs.getInt("rate");
						System.out.println(content);
						System.out.println("Rating:" + rate);
						System.out.println("------------------------");
					}
					System.out.println("1.Confirm");
					input = new Scanner(System.in);
					operate=input.nextInt();
				}else if(operate == 4) {
					finish = 0;
				}else if(operate == 0) {//delete
					System.out.println("------------------------");
					System.out.println("Are you sure you want to delete the account?");
					System.out.println("1.Confirm");
					System.out.println("2.Cancel");
					input = new Scanner(System.in);
					operate=input.nextInt();
					if(operate == 1) {
						sql = "DELETE FROM Users WHERE user_account_number =\""+ account_number + "\";";
						stmt.executeUpdate(sql);
						sql = "DELETE FROM Activity WHERE a_renter =\""+ account_number + "\";";
						stmt.executeUpdate(sql);
						System.out.println("Account Deleted!");
						finish = 0;
					}
				}
			}
			
			while(finish == 99) {				
				System.out.println("------------------------");
				System.out.println("VIEW REPORTS");
				System.out.println("1.Total number of bookings by zip_code and city");
				System.out.println("2.Number of shelters in country");
				System.out.println("3.Number of shelters in city");
				System.out.println("4.Number of shelters by zip-code");
				System.out.println("5.Ranking host by country");
				System.out.println("6.Ranking host by city");
				System.out.println("7.Search for hosts owning shelters more than 10% in country");
				System.out.println("8.Search for hosts owning shelters more than 10% in city");
				System.out.println("9.Rank ALL renters");
				System.out.println("10.Rank renters in country");
				System.out.println("11.View cancellation[Not Supported yet]");
				System.out.println("12.Seach pop words");
				System.out.println("0.Exit");
				@SuppressWarnings("resource")
				Scanner input = new Scanner(System.in);
				operate=input.nextInt();
				
				if(operate==1) {//number of booking
					System.out.println("------------------------");
					System.out.println("Country:");
					input = new Scanner(System.in);
					String country=input.nextLine();
					System.out.println("City:");
					input = new Scanner(System.in);
					String city=input.nextLine();
					System.out.println("zip_code:");
					input = new Scanner(System.in);
					String zip_code =input.nextLine();
					sql ="SELECT * FROM Activity,Shelters "
							+ "WHERE city = \""+city+"\" AND country = \""+country+"\" AND zip_code = \""+zip_code+"\" AND a_host = s_host AND a_shelter = s_name;";
					//System.out.println(sql);//
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql);
					int i = 0;
					while(rs.next()){
						i = i+1;
					}
					System.out.println("------------------------");
					System.out.println(i+" Activities Founded!");
					System.out.println("1.Confirm");
					input = new Scanner(System.in);
					operate=input.nextInt();
				}else if (operate == 2) {//s_country
					System.out.println("------------------------");
					System.out.println("Country:");
					input = new Scanner(System.in);
					String country=input.nextLine();
					sql ="SELECT * FROM Shelters WHERE country = \""+country+"\";";
					//System.out.println(sql);//
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql);
					int i = 0;
					while(rs.next()){
						i = i+1;
					}
					System.out.println("------------------------");
					System.out.println(i+" Shelters Founded!");
					System.out.println("1.Confirm");
					input = new Scanner(System.in);
					operate=input.nextInt();
				}else if (operate == 3) {//s_city
					System.out.println("------------------------");
					System.out.println("Country:");
					input = new Scanner(System.in);
					String country=input.nextLine();
					System.out.println("City:");
					input = new Scanner(System.in);
					String city=input.nextLine();
					sql ="SELECT * FROM Shelters WHERE city = \""+city+"\" AND country = \""+country+"\";";
					//System.out.println(sql);//
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql);
					int i = 0;
					while(rs.next()){
						i = i+1;
					}
					System.out.println("------------------------");
					System.out.println(i+" Shelters Founded!");
					System.out.println("1.Confirm");
					input = new Scanner(System.in);
					operate=input.nextInt();
				}else if (operate == 4) {//s_zip
					System.out.println("------------------------");
					System.out.println("zip_code:");
					input = new Scanner(System.in);
					String zip_code=input.nextLine();
					sql ="SELECT * FROM Shelters WHERE zip_code = \""+zip_code+"\";";
					//System.out.println(sql);//
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql);
					int i = 0;
					while(rs.next()){
						i = i+1;
					}
					System.out.println("------------------------");
					System.out.println(i+" Shelters Founded!");
					System.out.println("1.Confirm");
					input = new Scanner(System.in);
					operate=input.nextInt();
				}else if (operate == 5) {//ranking_country
					System.out.println("------------------------");
					System.out.println("Country:");
					input = new Scanner(System.in);
					String country=input.nextLine();
					sql ="SELECT s_host, user_name, AVG(rate) FROM Comments, Shelters, Users\r\n"
							+ "WHERE s_host = commented AND country = \""+country+"\" AND user_account_number = s_host\r\n"
							+ "GROUP BY s_host\r\n"
							+ "ORDER BY AVG(rate) DESC ;";
					//System.out.println(sql);//
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql);
					int i = 0;
					System.out.println("------------------------");
					while(rs.next()){
						i = i+1;
						String uname = rs.getString("user_name");
						String host = rs.getString("s_host");
						float rating = rs.getFloat("AVG(rate)");
						System.out.println(i+".Account Number["+host+"]"+uname+"| Average rating: "+rating);
					}
					System.out.println("1.Confirm");
					input = new Scanner(System.in);
					operate=input.nextInt();
				}else if (operate == 6) {//raking_city
					System.out.println("------------------------");
					System.out.println("Country:");
					input = new Scanner(System.in);
					String country=input.nextLine();
					System.out.println("City:");
					input = new Scanner(System.in);
					String city=input.nextLine();
					sql ="SELECT s_host, user_name, AVG(rate) FROM Comments, Shelters, Users\r\n"
							+ "WHERE s_host = commented AND country = \""+country+"\" AND city = \""+city+"\" AND user_account_number = s_host\r\n"
							+ "GROUP BY s_host\r\n"
							+ "ORDER BY AVG(rate) DESC ;";
					//System.out.println(sql);//
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql);
					int i = 0;
					System.out.println("------------------------");
					while(rs.next()){
						i = i+1;
						String uname = rs.getString("user_name");
						String host = rs.getString("s_host");
						float rating = rs.getFloat("AVG(rate)");
						System.out.println(i+".Account Number["+host+"]"+uname+"| Average rating: "+rating);
					}
					System.out.println("1.Confirm");
					input = new Scanner(System.in);
					operate=input.nextInt();
				}else if (operate == 7) {//10%country
					System.out.println("------------------------");
					System.out.println("Country:");
					input = new Scanner(System.in);
					String country=input.nextLine();
					sql ="SELECT * FROM "
							+ "(SELECT country,COUNT(1) AS count FROM Shelters "
							+ "GROUP BY country)AS C_LIST JOIN "
							+ "(SELECT s_host,COUNT(1) AS count FROM Shelters WHERE country = \""+country+"\" "
							+ "GROUP BY s_host)AS H_LIST "
							+ "WHERE C_LIST.country = \""+country+"\" AND 0.1*C_LIST.count < H_LIST.count;";
					//System.out.println(sql);//
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql);
					int i = 0;
					System.out.println("------------------------");
					while(rs.next()){
						i = i+1;
						String host = rs.getString("s_host");
						System.out.println(i+".Account Number: "+host);
					}
					System.out.println("1.Confirm");
					input = new Scanner(System.in);
					operate=input.nextInt();
				}else if (operate == 8) {//10%city
					System.out.println("------------------------");
					System.out.println("Country:");
					input = new Scanner(System.in);
					String country=input.nextLine();
					System.out.println("City:");
					input = new Scanner(System.in);
					String city=input.nextLine();
					sql ="SELECT * FROM "
							+ "(SELECT country,city,COUNT(1) AS count FROM Shelters "
							+ "GROUP BY country,city)AS C_LIST JOIN "
							+ "(SELECT s_host,COUNT(1) AS count FROM Shelters WHERE country = \""+country+"\" AND city = \""+city+"\" "
							+ "GROUP BY s_host)AS H_LIST "
							+ "WHERE C_LIST.country = \""+country+"\" AND city= \""+city+"\" AND 0.1*C_LIST.count < H_LIST.count;";
					//System.out.println(sql);//
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql);
					int i = 0;
					System.out.println("------------------------");
					while(rs.next()){
						i = i+1;
						String host = rs.getString("s_host");
						System.out.println(i+".Account Number: "+host);
					}
					System.out.println("1.Confirm");
					input = new Scanner(System.in);
					operate=input.nextInt();
				}else if (operate == 9) {//rank renters
					System.out.println("------------------------");
					sql ="SELECT user_account_number, user_name,COUNT(user_account_number) FROM Users, Activity "
							+ "WHERE user_account_number = a_renter "
							+ "GROUP BY user_account_number "
							+ "ORDER BY COUNT(user_account_number) DESC ;";
					//System.out.println(sql);//
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql);
					int i = 0;
					while(rs.next()){
						i = i+1;
						String user_account_number = rs.getString("user_account_number");
						String uname = rs.getString("user_name");
						int count = rs.getInt("COUNT(user_account_number)");
						System.out.println(i+".Account Number["+user_account_number+"] "+uname +"| Number of booking:"+count);
					}
					System.out.println("1.Confirm");
					input = new Scanner(System.in);
					operate=input.nextInt();
				}else if (operate == 10) {//rank renters in city
					System.out.println("------------------------");
					System.out.println("Country:");
					input = new Scanner(System.in);
					String country=input.nextLine();
					System.out.println("City:");
					input = new Scanner(System.in);
					String city=input.nextLine();
					System.out.println("------------------------");
					sql ="SELECT user_account_number, user_name,COUNT(user_account_number) FROM Users, Activity, Shelters "
							+ "WHERE user_account_number = a_renter AND a_shelter = s_name AND country = \""+country+"\" AND city = \""+city+"\" "
							+ "GROUP BY user_account_number "
							+ "ORDER BY COUNT(user_account_number) DESC ;";
					//System.out.println(sql);//
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql);
					int i = 0;
					while(rs.next()){
						i = i+1;
						String user_account_number = rs.getString("user_account_number");
						String uname = rs.getString("user_name");
						int count = rs.getInt("COUNT(user_account_number)");
						System.out.println(i+".Account Number["+user_account_number+"] "+uname +"| Number of booking:"+count);
					}
					System.out.println("1.Confirm");
					input = new Scanner(System.in);
					operate=input.nextInt();
				}else if (operate == 12) {//Search pop
					System.out.println("------------------------");
					System.out.println("Account Number of the commented user:");
					input = new Scanner(System.in);
					int user =input.nextInt();
					System.out.println("------------------------");
					sql ="SELECT WORD, COUNT(1) as COUNT "
							+ "FROM (SELECT substring_index(substring_index(content,\" \", help_topic_id + 1), \" \", -1)AS WORD "
							+ "FROM Comments JOIN mysql.help_topic ON help_topic_id<(LENGTH(content) - LENGTH(REPLACE(content, \" \", \"\"))+1) "
							+ "WHERE commented = \""+user+"\") AS WORD_LIST "
							+ "GROUP BY WORD ORDER BY COUNT(1) DESC ;";
					//System.out.println(sql);//
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql);
					int i = 0;
					while(rs.next() && i<1){
						i = i+1;
						String word = rs.getString("WORD");
						int count = rs.getInt("COUNT");
						System.out.println("MOST POPULAR WORD: "+word+" ["+count+"times]");
					}
					System.out.println("1.Confirm");
					input = new Scanner(System.in);
					operate=input.nextInt();
				}else if (operate==0) {
					finish = 0;
				}
				
			}
			
			System.out.println("------------------------");
			System.out.println("Closing connection...");
			if(rs != null) {rs.close();}
			if(rs != null) {stmt.close();}
			conn.close();
			System.out.println("Thank you for using!!!");
		} catch (SQLException e) {
			System.err.println("Plese enter a vaild value!");
		}
	}

}
