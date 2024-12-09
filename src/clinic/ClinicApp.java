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
import java.util.Optional;

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

        JPanel searchPanelLastName = new JPanel(new GridLayout(2, 2, 5, 5));
        JTextField searchFieldLastName = new JTextField();
        JButton searchButtonLastName = new JButton("Search by Last Name");

        patientList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(patientList);

        searchPanelLastName.add(new JLabel("Search by Last Name:"));
        searchPanelLastName.add(searchFieldLastName);
        searchPanelLastName.add(searchButtonLastName);

        searchButtonLastName.addActionListener(e -> {
            String lastName = searchFieldLastName.getText();
            List<clinic.model.Patient> patients = patientService.findPatientsByLastName(lastName);
            if (patients.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No patients found with last name: " + lastName, "No Results", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String[] patientArray = patients.stream()
                        .map(p -> p.getFirstName() + " " + p.getLastName() + " (" + p.getPesel() + ") " + p.getEmail() + " " + p.getPhoneNumber())
                        .toArray(String[]::new);
                patientList.setListData(patientArray);
            }
        });

        JPanel searchPanelPesel = new JPanel(new GridLayout(2, 2, 5, 5));
        JTextField searchFieldPesel = new JTextField();
        JButton searchButtonPesel = new JButton("Search by Pesel");

        searchPanelPesel.add(new JLabel("Search by Pesel:"));
        searchPanelPesel.add(searchFieldPesel);
        searchPanelPesel.add(searchButtonPesel);

        searchButtonPesel.addActionListener(e -> {
            String pesel = searchFieldPesel.getText();
            Optional<clinic.model.Patient> patient = patientService.findPatientByPesel(pesel);
            if (patient.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No patient found with pesel: " + pesel, "No Results", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String[] patientArray = patient.stream()
                        .map(p -> p.getFirstName() + " " + p.getLastName() + " (" + p.getPesel() + ") " + p.getEmail() + " " + p.getPhoneNumber())
                        .toArray(String[]::new);
                patientList.setListData(patientArray);
            }
        });
        
        JButton refreshButton = new JButton("Refresh List");
        refreshButton.addActionListener(e -> updatePatientList());

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));

        southPanel.add(searchPanelLastName);
        southPanel.add(searchPanelPesel);
        southPanel.add(refreshButton);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(southPanel, BorderLayout.SOUTH);
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
        JTextField dateOfBirthField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();

        formPanel.add(new JLabel("First Name:"));
        formPanel.add(firstNameField);
        formPanel.add(new JLabel("Last Name:"));
        formPanel.add(lastNameField);
        formPanel.add(new JLabel("Date of Birth (yyyy-MM-dd):"));
        formPanel.add(dateOfBirthField);
        formPanel.add(new JLabel("Phone Number:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Specialization(s):"));
        formPanel.add(specializationField);
        formPanel.add(new JLabel("ID:"));
        formPanel.add(idField);

        JButton createButton = new JButton("Create Doctor");
        createButton.addActionListener(e -> {
            try {
                doctorService.createDoctor(
                        firstNameField.getText(),
                        lastNameField.getText(),
                        idField.getText(),
                        LocalDate.parse(dateOfBirthField.getText(), dateFormatter),
                        phoneField.getText(),
                        emailField.getText()
                );
                
                try {
                    doctorService.addSpecializationToDoctor(idField.getText(), specializationField.getText());
                    specializationField.setText("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

                JOptionPane.showMessageDialog(this, "Doctor created successfully!");
                
                firstNameField.setText("");
                lastNameField.setText("");
                idField.setText("");
                dateOfBirthField.setText("");
                phoneField.setText("");
                emailField.setText("");

                updateDoctorList();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        doctorList = new JList<>();

        formPanel.add(createButton);

        JPanel addSpecializationPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JTextField addSpecializationFieldID = new JTextField();
        JTextField addSpecializationFieldSpecialization = new JTextField();
        JButton addSpecializationButton = new JButton("Add specialization");

        addSpecializationPanel.add(new JLabel("Enter doctors ID:"));
        addSpecializationPanel.add(addSpecializationFieldID);
        addSpecializationPanel.add(new JLabel("Enter Specialization to doctor to add:"));
        addSpecializationPanel.add(addSpecializationFieldSpecialization);
        addSpecializationPanel.add(addSpecializationButton);

        addSpecializationButton.addActionListener(e -> {
            String id = addSpecializationFieldID.getText();
            String specialization = addSpecializationFieldSpecialization.getText();
            
            try {
                doctorService.addSpecializationToDoctor(id, specialization);
                JOptionPane.showMessageDialog(this, "Specialization added successfully!");
                addSpecializationFieldSpecialization.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel searchPanelID = new JPanel(new GridLayout(2, 2, 5, 5));
        JTextField searchFieldID = new JTextField();
        JButton searchButtonID = new JButton("Search by ID");

        searchPanelID.add(new JLabel("Search by ID:"));
        searchPanelID.add(searchFieldID);
        searchPanelID.add(searchButtonID);

        searchButtonID.addActionListener(e -> {
            String id = searchFieldID.getText();
            Optional<clinic.model.Doctor> doctor = doctorService.findDoctorById(id);
            if (doctor.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No doctor found with id: " + id, "No Results", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String[] doctorArray = doctor.stream()
                        .map(p -> p.getFirstName() + " " + p.getLastName() + " (" + p.getId() + ") " + p.getEmail() + " " + p.getPhoneNumber() + " " + p.getSpecializations())
                        .toArray(String[]::new);
                doctorList.setListData(doctorArray);
            }
        });

        JPanel searchPanelSpecialization = new JPanel(new GridLayout(2, 2, 5, 5));
        JTextField searchFieldSpecialization = new JTextField();
        JButton searchButtonSpecialization = new JButton("Search by Specialization");

        searchPanelSpecialization.add(new JLabel("Search by Specialization:"));
        searchPanelSpecialization.add(searchFieldSpecialization);
        searchPanelSpecialization.add(searchButtonSpecialization);

        searchButtonSpecialization.addActionListener(e -> {
            String specialization = searchFieldSpecialization.getText();
            List<clinic.model.Doctor> doctor = doctorService.findDoctorsBySpecialization(specialization);
            if (doctor.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No doctor found with specialization: " + specialization, "No Results", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String[] doctorArray = doctor.stream()
                        .map(p -> p.getFirstName() + " " + p.getLastName() + " (" + p.getId() + ") " + p.getEmail() + " " + p.getPhoneNumber() + " " + p.getSpecializations())
                        .toArray(String[]::new);
                doctorList.setListData(doctorArray);
            }
        });

        JScrollPane scrollPane = new JScrollPane(doctorList);

        JButton refreshButton = new JButton("Refresh List");
        refreshButton.addActionListener(e -> updateDoctorList());

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));

        southPanel.add(addSpecializationPanel);
        southPanel.add(searchPanelID);
        southPanel.add(searchPanelSpecialization);
        southPanel.add(refreshButton);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.EAST);
        panel.add(southPanel, BorderLayout.SOUTH);

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
