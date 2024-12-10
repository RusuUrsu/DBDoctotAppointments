package org.example;

import org.example.Controller.Controller;
import org.example.model.*;
import org.example.Repository.*;
import org.example.Service.Service;
import org.example.UI.*;
import org.example.util.HibernateUtil;
import org.hibernate.SessionFactory;

public class App {
    public static void main(String[] args) {
//        InMemoryRepository<Appointment> appointmentInMemoryRepository = new InMemoryRepository<>();
//        InMemoryRepository<Clinic> clinicInMemoryRepository = new InMemoryRepository<>();
//        InMemoryRepository<Doctor> doctorInMemoryRepository = new InMemoryRepository<>();
//        InMemoryRepository<Patient> patientInMemoryRepository = new InMemoryRepository<>();
//        InMemoryRepository<Medication> medicationInMemoryRepository = new InMemoryRepository<>();
//        InMemoryRepository<Specialization> specializationInMemoryRepository = new InMemoryRepository<>();
//        Service service = new Service(appointmentInMemoryRepository, doctorInMemoryRepository, patientInMemoryRepository, clinicInMemoryRepository, medicationInMemoryRepository, specializationInMemoryRepository);
//        Controller controller = new Controller(service);
//        DoctorUI doctorUI = new DoctorUI(controller);
//        AdminUI adminUI = new AdminUI(controller);
//        PatientUI patientUI = new PatientUI(controller);
//        MainUI mainUI = new MainUI(controller, adminUI, doctorUI, patientUI);
//        mainUI.start();

//        FileRepository<Appointment> appointmentFileRepository = new FileRepository<>("org.example/Files/Appointment.txt");
//        FileRepository<Clinic> clinicFileRepository = new FileRepository<>("org.example/Files/Clinic.txt");
//        FileRepository<Doctor> doctorFileRepository = new FileRepository<>("org.example/Files/Doctor.txt");
//        FileRepository<Patient> patientFileRepository = new FileRepository<>("org.example/Files/Patient.txt");
//        FileRepository<Medication> medicationFileRepository = new FileRepository<>("org.example/Files/Medication.txt");
//        FileRepository<Specialization> specializationFileRepository= new FileRepository<>("org.example/Files/Specialization.txt");

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        DBRepo<Appointment> appointmentDBRepo = new DBRepo<>(sessionFactory, Appointment.class);
        DBRepo<Clinic> clinicDBRepo = new DBRepo<>(sessionFactory, Clinic.class);
        DBRepo<Doctor> doctorDBRepo = new DBRepo<>(sessionFactory, Doctor.class);
        DBRepo<Patient> patientDBRepo = new DBRepo<>(sessionFactory, Patient.class);
        DBRepo<Medication> medicationDBRepo = new DBRepo<>(sessionFactory, Medication.class);
        DBRepo<Specialization> specializationDBRepo = new DBRepo<>(sessionFactory, Specialization.class);

        Service service = new Service(appointmentDBRepo,doctorDBRepo,patientDBRepo,clinicDBRepo,medicationDBRepo,specializationDBRepo);
        Controller controller = new Controller(service);
        DoctorUI doctorUI = new DoctorUI(controller);
        AdminUI adminUI = new AdminUI(controller);
        PatientUI patientUI = new PatientUI(controller);
        MainUI mainUI = new MainUI(controller, adminUI, doctorUI, patientUI);
        mainUI.start();
    }
}
