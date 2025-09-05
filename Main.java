import java.io.*;
import java.util.*;

public class Main {
    private static final String USERS_FILE = "users.txt";

    // ---------------- User Registration ----------------
    private static void register(String username, String password) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE, true));
        bw.write(username + "," + password);
        bw.newLine();
        bw.close();
        System.out.println("✅ User registered successfully!");
    }

    // ---------------- User Login ----------------
    private static boolean login(String username, String password) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(USERS_FILE));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts[0].equals(username) && parts[1].equals(password)) {
                br.close();
                return true;
            }
        }
        br.close();
        return false;
    }

    // ---------------- Save Password Entry ----------------
    private static void savePassword(String username, String site, String siteUser, String sitePass) throws IOException {
        String fileName = username + "_passwords.txt";
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true));
        bw.write(site + "," + siteUser + "," + sitePass);
        bw.newLine();
        bw.close();
        System.out.println("🔑 Password saved successfully!");
    }

    // ---------------- View Passwords ----------------
    private static void viewPasswords(String username) throws IOException {
        String fileName = username + "_passwords.txt";
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("❌ No saved passwords found.");
            return;
        }

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        System.out.println("📂 Saved Passwords:");
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            System.out.println("Website: " + parts[0] + " | Username: " + parts[1] + " | Password: " + parts[2]);
        }
        br.close();
    }

    // ---------------- Main Menu ----------------
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== 🔐 Password Manager =====");

        while (true) {
            System.out.println("\n1. Register\n2. Login\n3. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            try {
                if (choice == 1) {
                    System.out.print("Enter new username: ");
                    String uname = sc.nextLine();
                    System.out.print("Enter new password: ");
                    String pass = sc.nextLine();
                    register(uname, pass);

                } else if (choice == 2) {
                    System.out.print("Enter username: ");
                    String uname = sc.nextLine();
                    System.out.print("Enter password: ");
                    String pass = sc.nextLine();

                    if (login(uname, pass)) {
                        System.out.println("✅ Login successful!");
                        while (true) {
                            System.out.println("\n--- Menu ---");
                            System.out.println("1. Save new password");
                            System.out.println("2. View saved passwords");
                            System.out.println("3. Logout");
                            System.out.print("Choose option: ");
                            int opt = sc.nextInt();
                            sc.nextLine();

                            if (opt == 1) {
                                System.out.print("Enter website name: ");
                                String site = sc.nextLine();
                                System.out.print("Enter site username: ");
                                String siteUser = sc.nextLine();
                                System.out.print("Enter site password: ");
                                String sitePass = sc.nextLine();
                                savePassword(uname, site, siteUser, sitePass);

                            } else if (opt == 2) {
                                viewPasswords(uname);

                            } else if (opt == 3) {
                                System.out.println("👋 Logged out.");
                                break;
                            } else {
                                System.out.println("❌ Invalid choice.");
                            }
                        }
                    } else {
                        System.out.println("❌ Invalid login credentials.");
                    }

                } else if (choice == 3) {
                    System.out.println("👋 Exiting...");
                    break;

                } else {
                    System.out.println("❌ Invalid choice.");
                }
            } catch (IOException e) {
                System.out.println("⚠ Error: " + e.getMessage());
            }
        }
        sc.close();
    }
    }
