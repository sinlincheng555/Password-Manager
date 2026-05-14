# Password Manager

A secure JavaFX-based password manager with AES-256/GCM encryption, user authentication, secure notes, and auto-lock functionality.

## Recommended Method

Clone the repository using the link below:
https://github.com/sinlincheng555/Password-Manager.git

### Steps:

1. Open IntelliJ IDEA
2. On the main menu (welcome screen), click **"Get from VCS"** / **"Clone Repository"** in the top right
3. Paste the GitHub link above into the URL field and click **Clone**
4. Once the project opens, navigate to `src/Main.java`
5. Run `Main.java` directly — this is the entry point for the application

---

## ⚠️ JavaFX Not Found / Cannot Run

If the project fails to run due to JavaFX not being detected, follow these steps:

1. Go to https://gluonhq.com/products/javafx/
2. Download **JavaFX 21.0.11** for your operating system (select SDK as the type)
3. Extract the downloaded folder somewhere easy to find (e.g. your Desktop)
4. In IntelliJ IDEA, go to **File → Project Structure**
5. On the left panel, click **Libraries**
6. Click the **+** button at the top
7. Select **Java** and navigate to the extracted JavaFX folder
8. Open the **lib** folder inside it and click **OK**
9. Click **Apply**, then **OK**
10. Run `Main.java` again — it should now work

---

## ⚠️ Note: Red Underlines in IDE

If any code is highlighted in red, this indicates unresolved dependencies or configuration issues in the IDE. You can still run `Main.java` without any problem — the red highlights are cosmetic warnings and do not prevent the program from executing.

To fully resolve them:

1. In the top menu, go to **File**
2. Click **Invalidate Caches / Restart...**
3. Confirm by clicking **Invalidate and Restart**

This clears the IDE's internal cache and re-indexes the project, removing the false error indicators. Your code and data will not be affected.