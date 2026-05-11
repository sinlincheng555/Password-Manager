# Password Manager

A secure password manager built in Java with JavaFX, 
developed in IntelliJ IDEA. For best  experiance use 
IntelliJ IDEA to run the code

---

### Step 1 — Download JavaFX SDK 23

1. Go to **https://gluonhq.com/products/javafx/**
2. Set the filters to:
    - Version: **23**
    - Operating System: **Windows**
    - Type: **SDK**
3. Click **Download**
4. Once downloaded, **extract** the zip file
5. Move the extracted folder to a simple path such as:
   ```
   C:\javafx-sdk-23\
   ```
   Avoid paths with spaces in the folder name

---

### Step 2 — Add JavaFX Library in IntelliJ

1. Open IntelliJ IDEA with the project open
2. Go to **File** → **Project Structure** (or press `Ctrl + Alt + Shift + S`)
3. On the left panel click **Libraries**
4. Click the **`+`** button at the top
5. Select **Java** from the dropdown
6. Navigate to your JavaFX folder and select the **`lib`** folder inside it:
   ```
   C:\javafx-sdk-23\lib
   ```
7. Click **OK**
8. Click **Apply** then **OK** to close Project Structure

---

### Step 3 — Add VM Options to Run Configuration

1. Click **Run** at the top menu → **Edit Configurations**
2. Select your run configuration on the left (usually called `Main`)
3. Look for a field called **VM Options**
    - If you do not see it, click **Modify Options** → tick **Add VM Options**
4. Paste the following into the VM Options field:
   ```
   --module-path "C:\javafx-sdk-23\lib" --add-modules javafx.controls,javafx.fxml
   ```
5. Click **Apply** then **OK**

