package clinic;

import clinic.service.AppointmentService;
import clinic.service.DoctorService;
import clinic.service.PatientService;
import clinic.service.ScheduleService;
import clinic.model.Appointment;
import clinic.model.Doctor;
import clinic.model.Patient;
import clinic.model.Schedule;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ClinicApp extends JFrame {
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final ScheduleService scheduleService;
    private final AppointmentService appointmentService;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public ClinicApp() {
        this.patientService = new PatientService();
        this.doctorService = new DoctorService();
        this.scheduleService = new ScheduleService(doctorService);
        this.appointmentService = new AppointmentService(patientService, doctorService, scheduleService);

        setTitle("Clinic Management System");
        setSize(800, 600);
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
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

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
        topPanel.add(backButton);
        backButton.addActionListener(e -> cardLayout.show(getContentPane(), "mainMenu"));


        JPanel centerPanel = new JPanel(new GridLayout(7, 2));
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField peselField = new JTextField();
        JTextField dateOfBirthField = new JTextField();
        JTextField phoneNumberField = new JTextField();
        JTextField emailField = new JTextField();
        JButton createPatientButton = new JButton("Create Patient");
        JList<String> patientList = new JList<>();
        JScrollPane patientScrollPane = new JScrollPane(patientList);

        centerPanel.add(new JLabel("First Name:"));
        centerPanel.add(firstNameField);
        centerPanel.add(new JLabel("Last Name:"));
        centerPanel.add(lastNameField);
        centerPanel.add(new JLabel("PESEL:"));
        centerPanel.add(peselField);
        centerPanel.add(new JLabel("Date of Birth (yyyy-MM-dd):"));
        centerPanel.add(dateOfBirthField);
        centerPanel.add(new JLabel("Phone Number:"));
        centerPanel.add(phoneNumberField);
        centerPanel.add(new JLabel("Email:"));
        centerPanel.add(emailField);
        centerPanel.add(createPatientButton);

        createPatientButton.addActionListener(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String pesel = peselField.getText();
            LocalDate dateOfBirth = LocalDate.parse(dateOfBirthField.getText(), dateFormatter);
            String phoneNumber = phoneNumberField.getText();
            String email = emailField.getText();

            patientService.createPatient(firstName, lastName, pesel, dateOfBirth, phoneNumber, email);
            JOptionPane.showMessageDialog(this, "Patient created successfully!");
            firstNameField.setText("");
            lastNameField.setText("");
            peselField.setText("");
            dateOfBirthField.setText("");
            phoneNumberField.setText("");
            emailField.setText("");
            updatePatientList(patientList);
        });

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(patientScrollPane, BorderLayout.SOUTH);

        updatePatientList(patientList);

        return panel;
    }

    private void updatePatientList(JList<String> patientList) {
        List<Patient> patients = patientService.getAllPatients();
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
        topPanel.add(backButton);
        backButton.addActionListener(e -> cardLayout.show(getContentPane(), "mainMenu"));


        JPanel centerPanel = new JPanel(new GridLayout(8, 2));
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField idField = new JTextField();
        JTextField dateOfBirthField = new JTextField();
        JTextField phoneNumberField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField specializationField = new JTextField();
        JButton createDoctorButton = new JButton("Create Doctor");
        JButton addSpecializationButton = new JButton("Add Specialization");
        JList<String> doctorList = new JList<>();
        JScrollPane doctorScrollPane = new JScrollPane(doctorList);

        centerPanel.add(new JLabel("First Name:"));
        centerPanel.add(firstNameField);
        centerPanel.add(new JLabel("Last Name:"));
        centerPanel.add(lastNameField);
        centerPanel.add(new JLabel("ID:"));
        centerPanel.add(idField);
        centerPanel.add(new JLabel("Date of Birth (yyyy-MM-dd):"));
        centerPanel.add(dateOfBirthField);
        centerPanel.add(new JLabel("Phone Number:"));
        centerPanel.add(phoneNumberField);
        centerPanel.add(new JLabel("Email:"));
        centerPanel.add(emailField);
        centerPanel.add(new JLabel("Specialization:"));
        centerPanel.add(specializationField);
        centerPanel.add(createDoctorButton);
        centerPanel.add(addSpecializationButton);

        createDoctorButton.addActionListener(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String id = idField.getText();
            LocalDate dateOfBirth = LocalDate.parse(dateOfBirthField.getText(), dateFormatter);
            String phoneNumber = phoneNumberField.getText();
            String email = emailField.getText();

            doctorService.createDoctor(firstName, lastName, id, dateOfBirth, phoneNumber, email);
            JOptionPane.showMessageDialog(this, "Doctor created successfully!");
            firstNameField.setText("");
            lastNameField.setText("");
            idField.setText("");
            dateOfBirthField.setText("");
            phoneNumberField.setText("");
            emailField.setText("");
            updateDoctorList(doctorList);
        });

        addSpecializationButton.addActionListener(e -> {
            String id = idField.getText();
            String specialization = specializationField.getText();
            doctorService.addSpecializationToDoctor(id, specialization);
            JOptionPane.showMessageDialog(this, "Specialization added successfully!");
            specializationField.setText("");
            updateDoctorList(doctorList);
        });

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(doctorScrollPane, BorderLayout.SOUTH);

        updateDoctorList(doctorList);

        return panel;
    }

    private void updateDoctorList(JList<String> doctorList) {
        List<Doctor> doctors = doctorService.getAllDoctors();
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
        topPanel.add(backButton);
        backButton.addActionListener(e -> cardLayout.show(getContentPane(), "mainMenu"));


        JPanel centerPanel = new JPanel(new GridLayout(5, 2));
        JTextField idField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField startTimeField = new JTextField();
        JTextField endTimeField = new JTextField();
        JButton createScheduleButton = new JButton("Create Schedule");
        JList<String> scheduleList = new JList<>();
        JScrollPane scheduleScrollPane = new JScrollPane(scheduleList);

        centerPanel.add(new JLabel("Doctor ID:"));
        centerPanel.add(idField);
        centerPanel.add(new JLabel("Date (yyyy-MM-dd):"));
        centerPanel.add(dateField);
        centerPanel.add(new JLabel("Start Time (HH:mm):"));
        centerPanel.add(startTimeField);
        centerPanel.add(new JLabel("End Time (HH:mm):"));
        centerPanel.add(endTimeField);
        centerPanel.add(createScheduleButton);

        createScheduleButton.addActionListener(e -> {
            String id = idField.getText();
            LocalDate date = LocalDate.parse(dateField.getText(), dateFormatter);
            LocalTime startTime = LocalTime.parse(startTimeField.getText(), timeFormatter);
            LocalTime endTime = LocalTime.parse(endTimeField.getText(), timeFormatter);

            scheduleService.createSchedule(id, date, startTime, endTime);
            JOptionPane.showMessageDialog(this, "Schedule created successfully!");
            idField.setText("");
            dateField.setText("");
            startTimeField.setText("");
            endTimeField.setText("");
            updateScheduleList(scheduleList);
        });

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(scheduleScrollPane, BorderLayout.SOUTH);

        updateScheduleList(scheduleList);

        return panel;
    }

    private void updateScheduleList(JList<String> scheduleList) {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        String[] scheduleArray = schedules.stream()
                .map(s -> s.getDoctor().getFirstName() + " " + s.getDoctor().getLastName() + " (" + s.getDoctor().getId() + ") - " + s.getAvailability())
                .toArray(String[]::new);
        scheduleList.setListData(scheduleArray);
    }

    private JPanel createAppointmentPanel(CardLayout cardLayout) {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel appointmentLabel = new JLabel("Appointment Management");
        topPanel.add(appointmentLabel);

        JButton backButton = new JButton("Back to Main Menu");
        topPanel.add(backButton);
        backButton.addActionListener(e -> cardLayout.show(getContentPane(), "mainMenu"));


        JPanel centerPanel = new JPanel(new GridLayout(5, 2));
        JTextField patientPeselField = new JTextField();
        JTextField doctorIdField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField timeField = new JTextField();
        JButton createAppointmentButton = new JButton("Create Appointment");
        JList<String> appointmentList = new JList<>();
        JScrollPane appointmentScrollPane = new JScrollPane(appointmentList);

        centerPanel.add(new JLabel("Patient PESEL:"));
        centerPanel.add(patientPeselField);
        centerPanel.add(new JLabel("Doctor ID:"));
        centerPanel.add(doctorIdField);
        centerPanel.add(new JLabel("Date (yyyy-MM-dd):"));
        centerPanel.add(dateField);
        centerPanel.add(new JLabel("Time (HH:mm):"));
        centerPanel.add(timeField);
        centerPanel.add(createAppointmentButton);

        createAppointmentButton.addActionListener(e -> {
            String patientPesel = patientPeselField.getText();
            String doctorId = doctorIdField.getText();
            LocalDate date = LocalDate.parse(dateField.getText(), dateFormatter);
            LocalTime time = LocalTime.parse(timeField.getText(), timeFormatter);

            try {
                appointmentService.createAppointment(patientPesel, doctorId, date, time);
                JOptionPane.showMessageDialog(this, "Appointment created successfully!");
                patientPeselField.setText("");
                doctorIdField.setText("");
                dateField.setText("");
                timeField.setText("");
                updateAppointmentList(appointmentList, doctorId, date);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(appointmentScrollPane, BorderLayout.SOUTH);

        return panel;
    }

    private void updateAppointmentList(JList<String> appointmentList, String doctorId, LocalDate date) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctorAndDate(doctorId, date);
        String[] appointmentArray = appointments.stream()
                .map(a -> a.getPatient().getFirstName() + " " + a.getPatient().getLastName() + " (" + a.getTime().format(timeFormatter) + ")")
                .toArray(String[]::new);
        appointmentList.setListData(appointmentArray);
    }

public static void main(String[] args) {
    SwingUtilities.invokeLater(ClinicApp::new);
}

}