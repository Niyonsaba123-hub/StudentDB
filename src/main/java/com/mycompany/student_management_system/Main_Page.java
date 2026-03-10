package com.mycompany.student_management_system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Main_Page extends JFrame {
    // Components for student information
    private JTextField nameField, emailField, marksField;
private JComboBox<String> courseField;
    private JTable studentsTable;
    private DefaultTableModel tableModel;
    private JLabel statusLabel;
    
    // For search and filter functionality
    private TableRowSorter<DefaultTableModel> rowSorter;
    private List<Student> students = new ArrayList<>();
    private List<Student> filteredStudents = new ArrayList<>();
    private boolean isFiltered = false;
    private int currentId = 1;
    
    public Main_Page() {
        setTitle("Student Management System");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Initialize sample data
        initSampleData();
        
        // Create menu bar
        setupMenuBar();
        
        // Create main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Top panel with welcome message
        mainPanel.add(createTopPanel(), BorderLayout.NORTH);
        
        // Center panel with split pane (information + table)
        mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
        
        // Bottom panel with options and buttons
        mainPanel.add(createBottomPanel(), BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // Load initial data
        refreshTable();
    }
    
    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // File menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        
        // Students menu
        JMenu studentsMenu = new JMenu("Students");
        JMenuItem addItem = new JMenuItem("Add New Student");
        addItem.addActionListener(e -> clearForm());
        studentsMenu.add(addItem);
        
        JMenuItem showAllItem = new JMenuItem("Show All Records");
        showAllItem.addActionListener(e -> showAllRecords());
        studentsMenu.add(showAllItem);
        
        // Help menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutItem);
        
        menuBar.add(fileMenu);
        menuBar.add(studentsMenu);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
    }
    
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(240, 248, 255));
        
        JLabel welcomeLabel = new JLabel("Welcome To Student Management System", 
                                          SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(0, 102, 204));
        
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        statusLabel.setForeground(new Color(0, 153, 0));
        
        topPanel.add(welcomeLabel, BorderLayout.CENTER);
        topPanel.add(statusLabel, BorderLayout.SOUTH);
        
        return topPanel;
    }
    
    private JPanel createCenterPanel() {
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(400);
        splitPane.setResizeWeight(0.3);
        
        // Left panel - Student Information Form
        splitPane.setLeftComponent(createStudentInfoPanel());
        
        // Right panel - Students Records Table
        splitPane.setRightComponent(createRecordsPanel());
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(splitPane, BorderLayout.CENTER);
        
        return centerPanel;
    }
    
    private JPanel createStudentInfoPanel() {
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Students Information"));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        
        // Names
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Names:"), gbc);
        
        gbc.gridy = 1;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);
        
        // Email
        gbc.gridy = 2;
        formPanel.add(new JLabel("Email:"), gbc);
        
        gbc.gridy = 3;
        emailField = new JTextField(20);
        formPanel.add(emailField, gbc);
        
        // Course
        gbc.gridy = 4;
        formPanel.add(new JLabel("Course:"), gbc);
        
        gbc.gridy = 5;
        String[] courses = {
    "Computer Engineering",
    "Computer Science",
    "Information Technology",
    "Civil Engineering",
    "Mathematics",
    "Physics"
};

courseField = new JComboBox<>(courses);
formPanel.add(courseField, gbc);
        
        // Marks
        gbc.gridy = 6;
        formPanel.add(new JLabel("Marks:"), gbc);
        
        gbc.gridy = 7;
        marksField = new JTextField(20);
        formPanel.add(marksField, gbc);
        
        // Marks range info
        gbc.gridy = 8;
        JLabel rangeLabel = new JLabel("Marks ranges from 0 - 100");
        rangeLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        rangeLabel.setForeground(Color.GRAY);
        formPanel.add(rangeLabel, gbc);
        
        infoPanel.add(formPanel, BorderLayout.CENTER);
        
        return infoPanel;
    }
    
    private JPanel createRecordsPanel() {
        JPanel recordsPanel = new JPanel(new BorderLayout());
        recordsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Students Records"));
        
        // Create table
        String[] columns = {"ID", "Names", "Email", "Course", "Marks"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        
        studentsTable = new JTable(tableModel);
        
        // Set up row sorter for filtering
        rowSorter = new TableRowSorter<>(tableModel);
        studentsTable.setRowSorter(rowSorter);
        
        studentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedStudent();
            }
        });
        
        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(studentsTable);
        studentsTable.setFillsViewportHeight(true);
        
        recordsPanel.add(scrollPane, BorderLayout.CENTER);
        
        return recordsPanel;
    }
    
    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBorder(BorderFactory.createEtchedBorder());
        
        // Options panel
        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        optionsPanel.setBorder(BorderFactory.createTitledBorder("Options"));
        
        // Sort options
        optionsPanel.add(new JLabel("Sort By:"));
        JRadioButton descButton = new JRadioButton("Descending");
        JRadioButton ascButton = new JRadioButton("Ascending");
        ButtonGroup sortGroup = new ButtonGroup();
        sortGroup.add(descButton);
        sortGroup.add(ascButton);
        ascButton.setSelected(true);
        
        // Add sort listeners
        descButton.addActionListener(e -> sortTable(false));
        ascButton.addActionListener(e -> sortTable(true));
        
        optionsPanel.add(descButton);
        optionsPanel.add(ascButton);
        
        // Pass/Fail checkboxes
        JCheckBox passCheck = new JCheckBox("Pass");
        JCheckBox failCheck = new JCheckBox("Fail");
        
        // Add filter listeners
        passCheck.addActionListener(e -> filterByPassFail(passCheck, failCheck));
        failCheck.addActionListener(e -> filterByPassFail(passCheck, failCheck));
        
        optionsPanel.add(passCheck);
        optionsPanel.add(failCheck);
        
        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        // Create buttons with styling
        JButton addButton = createStyledButton("+ Add", new Color(0, 153, 76));
        JButton updateButton = createStyledButton("Update", new Color(255, 153, 0));
        JButton deleteButton = createStyledButton("Delete", new Color(204, 0, 0));
        JButton searchButton = createStyledButton("Search", new Color(0, 102, 204));
        JButton showAllButton = createStyledButton("Show All", new Color(102, 0, 204));
        JButton adminButton = createStyledButton("Admin", new Color(102, 51, 153));
        JButton computerButton = createStyledButton("Computer E...", new Color(0, 153, 153));
        
        // Add action listeners
        addButton.addActionListener(e -> addStudent());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        searchButton.addActionListener(e -> searchStudent());
        showAllButton.addActionListener(e -> showAllRecords());
        
        buttonsPanel.add(addButton);
        buttonsPanel.add(updateButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(searchButton);
        buttonsPanel.add(showAllButton);
        buttonsPanel.add(adminButton);
        buttonsPanel.add(computerButton);
        
        bottomPanel.add(optionsPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonsPanel, BorderLayout.CENTER);
        
        return bottomPanel;
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        return button;
    }
    
    private void initSampleData() {
        // Add sample data
//        students.add(new Student(currentId++, "Jean D Amour Niyonsaba", 
//                                "jean@gmail.com", "Computer Engineering", 97));
//        students.add(new Student(currentId++, "Emelyne", 
//                                "em@gmail.com", "Comp Engineering", 97));
//        students.add(new Student(currentId++, "David", 
//                                "david@gmail.com", "Civil Engineering", 80));
//        students.add(new Student(currentId++, "Moise", 
//                                "moise@gmail.com", "Inform Techn", 78));
//        students.add(new Student(currentId++, "Egide", 
//                                "egide@gmail.com", "Info System", 70));
//        students.add(new Student(currentId++, "Alice", 
//                                "alice@gmail.com", "Computer Science", 85));
//        students.add(new Student(currentId++, "Bob", 
//                                "bob@gmail.com", "Mathematics", 92));
//        students.add(new Student(currentId++, "Charlie", 
          //                      "charlie@gmail.com", "Physics", 88));
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Student student : students) {
            tableModel.addRow(new Object[]{
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getCourse(),
                student.getMarks()
            });
        }
        isFiltered = false;
        showStatus("Showing all " + students.size() + " records", true);
    }
    
    private void clearForm() {
    nameField.setText("");
    emailField.setText("");
    courseField.setSelectedIndex(0);
    marksField.setText("");
}
    private void addStudent() {
        try {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
           String course = courseField.getSelectedItem().toString();
            int marks = Integer.parseInt(marksField.getText().trim());
            
            if (name.isEmpty() || email.isEmpty() || course.isEmpty()) {
                showStatus("Please fill all fields!", false);
                return;
            }
            
            if (marks < 0 || marks > 100) {
                showStatus("Marks must be between 0 and 100!", false);
                return;
            }
            
            Student student = new Student(currentId++, name, email, course, marks);
            students.add(student);
            
            refreshTable();
            clearForm();
            showStatus("Record Added Successfully!", true);
            
        } catch (NumberFormatException ex) {
            showStatus("Invalid marks format!", false);
        }
    }
    
    private void updateStudent() {
        int selectedRow = studentsTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Convert view row to model row (important when filter is applied)
            int modelRow = studentsTable.convertRowIndexToModel(selectedRow);
            int id = (int) tableModel.getValueAt(modelRow, 0);
            
            try {
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
               String course = courseField.getSelectedItem().toString();
                int marks = Integer.parseInt(marksField.getText().trim());
                
                if (name.isEmpty() || email.isEmpty() || course.isEmpty()) {
                    showStatus("Please fill all fields!", false);
                    return;
                }
                
                if (marks < 0 || marks > 100) {
                    showStatus("Marks must be between 0 and 100!", false);
                    return;
                }
                
                for (Student student : students) {
                    if (student.getId() == id) {
                        student.setName(name);
                        student.setEmail(email);
                        student.setCourse(course);
                        student.setMarks(marks);
                        break;
                    }
                }
                
                refreshTable();
                clearForm();
                showStatus("Record Updated Successfully!", true);
                
            } catch (NumberFormatException ex) {
                showStatus("Invalid marks format!", false);
            }
        } else {
            showStatus("Please select a student to update!", false);
        }
    }
    
    private void deleteStudent() {
        int selectedRow = studentsTable.getSelectedRow();
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this record?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                // Convert view row to model row
                int modelRow = studentsTable.convertRowIndexToModel(selectedRow);
                int id = (int) tableModel.getValueAt(modelRow, 0);
                
                students.removeIf(student -> student.getId() == id);
                refreshTable();
                clearForm();
                showStatus("Record Deleted Successfully!", true);
            }
        } else {
            showStatus("Please select a student to delete!", false);
        }
    }
    
    private void searchStudent() {
        // Create a custom search dialog
        JPanel searchPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        
        JComboBox<String> searchTypeCombo = new JComboBox<>(
            new String[]{"Name", "ID", "Email", "Course", "Marks"});
        JTextField searchField = new JTextField(20);
        
        searchPanel.add(new JLabel("Search by:"));
        searchPanel.add(searchTypeCombo);
        searchPanel.add(new JLabel("Enter search term:"));
        searchPanel.add(searchField);
        
        int result = JOptionPane.showConfirmDialog(this, searchPanel, 
            "Search Student", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String searchTerm = searchField.getText().trim().toLowerCase();
            String searchType = (String) searchTypeCombo.getSelectedItem();
            
            if (!searchTerm.isEmpty()) {
                filterTable(searchTerm, searchType);
            } else {
                showStatus("Please enter a search term!", false);
            }
        }
    }
    
    private void filterTable(String searchTerm, String searchType) {
        List<Student> filteredList = new ArrayList<>();
        
        for (Student student : students) {
            boolean matches = false;
            
            switch (searchType) {
                case "Name":
                    matches = student.getName().toLowerCase().contains(searchTerm);
                    break;
                case "ID":
                    matches = String.valueOf(student.getId()).equals(searchTerm);
                    break;
                case "Email":
                    matches = student.getEmail().toLowerCase().contains(searchTerm);
                    break;
                case "Course":
                    matches = student.getCourse().toLowerCase().contains(searchTerm);
                    break;
                case "Marks":
                    try {
                        int marks = Integer.parseInt(searchTerm);
                        matches = student.getMarks() == marks;
                    } catch (NumberFormatException e) {
                        // If not a number, don't match
                    }
                    break;
            }
            
            if (matches) {
                filteredList.add(student);
            }
        }
        
        if (!filteredList.isEmpty()) {
            displayFilteredResults(filteredList);
            showStatus("Found " + filteredList.size() + " matching record(s)", true);
        } else {
            showStatus("No matching records found!", false);
        }
    }
    
    private void displayFilteredResults(List<Student> filteredList) {
        tableModel.setRowCount(0);
        for (Student student : filteredList) {
            tableModel.addRow(new Object[]{
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getCourse(),
                student.getMarks()
            });
        }
        isFiltered = true;
    }
    
    private void showAllRecords() {
        refreshTable();
        showStatus("Showing all " + students.size() + " records", true);
    }
    
    private void sortTable(boolean ascending) {
        List<Student> listToSort = isFiltered ? getCurrentTableData() : students;
        
        if (ascending) {
            listToSort.sort((s1, s2) -> s1.getName().compareTo(s2.getName()));
        } else {
            listToSort.sort((s1, s2) -> s2.getName().compareTo(s1.getName()));
        }
        
        // Refresh table display
        tableModel.setRowCount(0);
        for (Student student : listToSort) {
            tableModel.addRow(new Object[]{
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getCourse(),
                student.getMarks()
            });
        }
        
        showStatus("Records sorted " + (ascending ? "ascending" : "descending"), true);
    }
    
    private List<Student> getCurrentTableData() {
        List<Student> currentData = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            int id = (int) tableModel.getValueAt(i, 0);
            String name = (String) tableModel.getValueAt(i, 1);
            String email = (String) tableModel.getValueAt(i, 2);
            String course = (String) tableModel.getValueAt(i, 3);
            int marks = (int) tableModel.getValueAt(i, 4);
            
            currentData.add(new Student(id, name, email, course, marks));
        }
        return currentData;
    }
    
    private void filterByPassFail(JCheckBox passCheck, JCheckBox failCheck) {
        boolean showPass = passCheck.isSelected();
        boolean showFail = failCheck.isSelected();
        
        // If both are selected or neither is selected, show all
        if ((showPass && showFail) || (!showPass && !showFail)) {
            refreshTable();
            return;
        }
        
        List<Student> filteredList = new ArrayList<>();
        int passCount = 0, failCount = 0;
        
        for (Student student : students) {
            boolean isPass = student.getMarks() >= 50;
            
            if (showPass && isPass) {
                filteredList.add(student);
                passCount++;
            } else if (showFail && !isPass) {
                filteredList.add(student);
                failCount++;
            }
        }
        
        if (!filteredList.isEmpty()) {
            displayFilteredResults(filteredList);
            if (showPass) {
                showStatus("Showing " + passCount + " students who passed", true);
            } else {
                showStatus("Showing " + failCount + " students who failed", true);
            }
        } else {
            showStatus("No students match the selected criteria", false);
        }
    }
    
    private void loadSelectedStudent() {
        int selectedRow = studentsTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Convert view row to model row
            int modelRow = studentsTable.convertRowIndexToModel(selectedRow);
            nameField.setText(tableModel.getValueAt(modelRow, 1).toString());
            emailField.setText(tableModel.getValueAt(modelRow, 2).toString());
courseField.setSelectedItem(tableModel.getValueAt(modelRow, 3).toString());
            marksField.setText(tableModel.getValueAt(modelRow, 4).toString());
        }
    }
    
    private void showStatus(String message, boolean success) {
        statusLabel.setText(message);
        statusLabel.setForeground(success ? new Color(0, 153, 0) : Color.RED);
        
        // Hide status after 3 seconds
        Timer timer = new Timer(3000, e -> statusLabel.setText(""));
        timer.setRepeats(false);
        timer.start();
    }
    
    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this,
            "Student Management System\nVersion 1.0\n\n" +
            "A simple application to manage student records.\n\n" +
            "Features:\n" +
            "• Add, Update, Delete student records\n" +
            "• Search by Name, ID, Email, Course, or Marks\n" +
            "• Show All Records\n" +
            "• Filter by Pass/Fail\n" +
            "• Sort records",
            "About",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Student inner class
    private class Student {
        private int id;
        private String name;
        private String email;
        private String course;
        private int marks;
        
        public Student(int id, String name, String email, String course, int marks) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.course = course;
            this.marks = marks;
        }
        
        // Getters and setters
        public int getId() { return id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getCourse() { return course; }
        public void setCourse(String course) { this.course = course; }
        public int getMarks() { return marks; }
        public void setMarks(int marks) { this.marks = marks; }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main_Page().setVisible(true);
        });
    }
}