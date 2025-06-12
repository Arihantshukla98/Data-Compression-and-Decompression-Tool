# 🗜️ DataSankuchak

**DataSankuchak** is a powerful and user-friendly file compression and decompression tool built using Java. It utilizes **Huffman Coding** for efficient lossless data compression and features a clean, aesthetic **GUI** that supports dark mode, real-time animations, and intuitive user interactions.

## 🚀 Features

- 🔄 **Compression & Decompression** of files using Huffman Coding  
- 🎨 **Modern GUI** with animated buttons and theme toggling (Dark/Light mode)  
- 🌙 **Dark Mode** toggle with sun/moon icon animation  
- 📁 **File Chooser Dialog** to easily select files  
- ✅ **Completion Pop-up** showing compression path and statistics  
- 📊 Real-time feedback on file size reduction  

## 🧱 Built With

- **Java**
- **Swing/AWT** for GUI
- **Custom Huffman Coding** algorithm implementation

## 📸 Screenshots

> _Insert screenshots of your GUI here (Compression Window, Dark Mode, Output Pop-up, etc.)_

## 📂 How to Use

### 🖥️ Run the Tool

1. Clone or download the project.
2. Compile using:
   ```bash
   javac DataSankuchak.java
   ```
3. Run the program:
   ```bash
   java DataSankuchak
   ```

### 🧰 GUI Usage

- **Select File**: Choose the file you want to compress or decompress.
- **Compress**: Click to compress the selected file using Huffman Coding.
- **Decompress**: Click to restore a previously compressed `.huff` file.
- **Toggle Theme**: Switch between Light and Dark modes via the top-right corner.

## 📊 Compression Algorithm: Huffman Coding

This tool uses Huffman Coding, a lossless compression technique that assigns shorter codes to more frequent characters and longer codes to less frequent ones, optimizing the overall file size.

## 📦 Project Structure

```
DataSankuchak/
│
├── icons/                   # Icons for theme toggle (sun/moon)
├── AppFrame.java            # Main GUI application
├── Compressor.java          # Compression logic
├── Decompressor.java        # Decompression logic
├── Main.java                # MAIN
└── README.md                # This file
```

## 📈 Performance

- Achieves compression ratios typically between **30-70%** (varies by file type).
- Supports all text-based files (e.g., `.txt`, `.csv`, `.java`, etc.).

## ❗ Limitations

- Currently supports **text-based files only**.
- No drag-and-drop support (planned for future updates).

## 🛠️ Future Improvements

- 📂 Drag-and-drop file support
- 🧪 Multi-file batch compression
- 🗃️ File type previews
- 📱 Portable executable (.jar) packaging

## 🧑‍💻 Author

**Arihant Shukla**  
**Bhavya Saini**  
B.Tech CSE | New Horizon College  

