# Password Manager

A secure password manager built in Java with JavaFX, developed in IntelliJ IDEA.

---

## Requirements

- Java JDK 23
- JavaFX SDK 23
- IntelliJ IDEA (recommended)

---

## Running the Project

Clone the repository and open it in IntelliJ IDEA. If the project runs without any errors, you are good to go.

If you get a JavaFX error on startup, follow the troubleshooting guide below.

---

## JavaFX Troubleshooting Guide

If you see an error like:
```
Error occurred during initialization of boot layer
java.lang.module.FindException: Module javafx.controls not found
```
or
```
Unsupported major.minor version
```

Follow these steps exactly.

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

---

## Common Mistakes

| Mistake | Fix |
|---|---|
| Downloaded JavaFX 26 or another version | Re-download and make sure it says version **23** |
| Pointed to the wrong folder | Make sure you selected the `lib` folder **inside** the SDK, not the SDK root |
| VM Options field is empty | Re-check Step 3 and make sure you clicked Apply |
| JDK version is not 23 | Go to File → Project Structure → SDK and set it to JDK 23 |
| Path has spaces in it | Move the JavaFX folder to `C:\javafx-sdk-23\` with no spaces |

---

## Why Version 23 Specifically

This project was developed using **JDK 23**. JavaFX must match the JDK version exactly. Using JavaFX 26 with JDK 23 will cause an `Unsupported major.minor version` error because the JARs are compiled for a newer Java version than you have installed.

---
