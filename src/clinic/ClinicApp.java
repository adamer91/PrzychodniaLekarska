package clinic;

import clinic.service.AppointmentService;
import clinic.service.DoctorService;
import clinic.service.PatientService;
import clinic.service.ScheduleService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ClinicApp extends JFrame {
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final ScheduleService scheduleService;
    private final AppointmentService appointmentService;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private JList<String> patientList;
    private JList<String> doctorList;

    public ClinicApp() {
        this.patientService = new PatientService();
        this.doctorService = new DoctorService();
        this.scheduleService = new ScheduleService(doctorService);
        this.appointmentService = new AppointmentService(patientService, doctorService, scheduleService);

        setTitle("Clinic Management System");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel cardPanel = new JPanel(new CardLayout());
        CardLayout cardLayout = (CardLayout) cardPanel.getLayout();

        JPanel patientPanel = createPatientPanel(cardLayout);
        JPanel doctorPanel = createDoctorPanel(cardLayout);
        JPanel schedulePanel = createSchedulePanel(cardLayout);
        JPanel appointmentPanel = createAppointmentPanel(cardLayout);
        JPanel mainMenuPanel = createMainMenuPanel(cardLayout);

        cardPanel.add(mainMenuPanel, "mainMenu");
        cardPanel.add(patientPanel, "patient");
        cardPanel.add(doctorPanel, "doctor");
        cardPanel.add(schedulePanel, "schedule");
        cardPanel.add(appointmentPanel, "appointment");

        setContentPane(cardPanel);
        cardLayout.show(cardPanel, "mainMenu");

        setVisible(true);
    }

    private JPanel createMainMenuPanel(CardLayout cardLayout) {
        JPanel panel = new JPanel(new GridLayout(4, 1));

        JButton patientButton = new JButton("Patient Management");
        JButton doctorButton = new JButton("Doctor Management");
        JButton scheduleButton = new JButton("Schedule Management");
        JButton appointmentButton = new JButton("Appointment Management");

        patientButton.addActionListener(e -> cardLayout.show(getContentPane(), "patient"));
        doctorButton.addActionListener(e -> cardLayout.show(getContentPane(), "doctor"));
        scheduleButton.addActionListener(e -> cardLayout.show(getContentPane(), "schedule"));
        appointmentButton.addActionListener(e -> cardLayout.show(getContentPane(), "appointment"));

        panel.add(patientButton);
        panel.add(doctorButton);
        panel.add(scheduleButton);
        panel.add(appointmentButton);

        return panel;
    }

    private JPanel createPatientPanel(CardLayout cardLayout) {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel patientLabel = new JLabel("Patient Management");
        topPanel.add(patientLabel);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> cardLayout.show(getContentPane(), "mainMenu"));
        topPanel.add(backButton);

        JPanel formPanel = new JPanel(new GridLayout(8, 2, 5, 5));
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField peselField = new JTextField();
        JTextField dateOfBirthField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();

        formPanel.add(new JLabel("First Name:"));
        formPanel.add(firstNameField);
        formPanel.add(new JLabel("Last Name:"));
        formPanel.add(lastNameField);
        formPanel.add(new JLabel("PESEL:"));
        formPanel.add(peselField);
        formPanel.add(new JLabel("Date of Birth (yyyy-MM-dd):"));
        formPanel.add(dateOfBirthField);
        formPanel.add(new JLabel("Phone Number:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);

        JButton createButton = new JButton("Create Patient");
        createButton.addActionListener(e -> {
            try {
                patientService.createPatient(
                        firstNameField.getText(),
                        lastNameField.getText(),
                        peselField.getText(),
                        LocalDate.parse(dateOfBirthField.getText(), dateFormatter),
                        phoneField.getText(),
                        emailField.getText()
                );
                JOptionPane.showMessageDialog(this, "Patient created successfully!");

                firstNameField.setText("");
                lastNameField.setText("");
                peselField.setText("");
                dateOfBirthField.setText("");
                phoneField.setText("");
                emailField.setText("");

                updatePatientList();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        formPanel.add(createButton);

        JPanel searchPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        JTextField searchField = new JTextField();
        JButton searchButton = new JButton("Search by Last Name");

        patientList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(patientList);

        searchPanel.add(new JLabel("Search by Last Name:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        searchButton.addActionListener(e -> {
            String lastName = searchField.getText();
            List<clinic.model.Patient> patients = patientService.findPatientsByLastName(lastName);
            if (patients.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No patients found with last name: " + lastName, "No Results", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String[] patientArray = patients.stream()
                        .map(p -> p.getFirstName() + " " + p.getLastName() + " (" + p.getPesel() + ")")
                        .toArray(String[]::new);
                patientList.setListData(patientArray);
            }
        });

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(searchPanel, BorderLayout.SOUTH);
        panel.add(scrollPane, BorderLayout.EAST);

        return panel;
    }

    private void updatePatientList() {
        List<clinic.model.Patient> patients = patientService.getAllPatients();
        String[] patientArray = patients.stream()
                .map(p -> p.getFirstName() + " " + p.getLastName() + " (" + p.getPesel() + ")")
                .toArray(String[]::new);
        patientList.setListData(patientArray);
    }

    private JPanel createDoctorPanel(CardLayout cardLayout) {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel doctorLabel = new JLabel("Doctor Management");
        topPanel.add(doctorLabel);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> cardLayout.show(getContentPane(), "mainMenu"));
        topPanel.add(backButton);

        JPanel formPanel = new JPanel(new GridLayout(8, 2, 5, 5));
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField idField = new JTextField();
        JTextField specializationField = new JTextField();

        formPanel.add(new JLabel("First Name:"));
        formPanel.add(firstNameField);
        formPanel.add(new JLabel("Last Name:"));
        formPanel.add(lastNameField);
        formPanel.add(new JLabel("ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Specialization:"));
        formPanel.add(specializationField);

        JButton createButton = new JButton("Create Doctor");
        createButton.addActionListener(e -> {
            try {
                doctorService.createDoctor(
                        firstNameField.getText(),
                        lastNameField.getText(),
                        idField.getText(),
                        LocalDate.now(),
                        "123456789",
                        "doctor@example.com"
                );
                JOptionPane.showMessageDialog(this, "Doctor created successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton addSpecializationButton = new JButton("Add Specialization");
        addSpecializationButton.addActionListener(e -> {
            try {
                doctorService.addSpecializationToDoctor(idField.getText(), specializationField.getText());
                JOptionPane.showMessageDialog(this, "Specialization added successfully!");
                specializationField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        formPanel.add(createButton);
        formPanel.add(addSpecializationButton);

        doctorList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(doctorList);

        JButton refreshButton = new JButton("Refresh List");
        refreshButton.addActionListener(e -> updateDoctorList());

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.EAST);
        panel.add(refreshButton, BorderLayout.SOUTH);

        return panel;
    }

    private void updateDoctorList() {
        List<clinic.model.Doctor> doctors = doctorService.getAllDoctors();
        String[] doctorArray = doctors.stream()
                .map(d -> d.getFirstName() + " " + d.getLastName() + " (" + d.getId() + ") - " + d.getSpecializations())
                .toArray(String[]::new);
        doctorList.setListData(doctorArray);
    }

    private JPanel createSchedulePanel(CardLayout cardLayout) {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel scheduleLabel = new JLabel("Schedule Management");
        topPanel.add(scheduleLabel);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> cardLayout.show(getContentPane(), "mainMenu"));
        topPanel.add(backButton);

        panel.add(topPanel, BorderLayout.NORTH);

        return panel;
    }

    private JPanel createAppointmentPanel(CardLayout cardLayout) {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel appointmentLabel = new JLabel("Appointment Management");
        topPanel.add(appointmentLabel);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> cardLayout.show(getContentPane(), "mainMenu"));
        topPanel.add(backButton);

        panel.add(topPanel, BorderLayout.NORTH);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClinicApp::new);
    }
}
