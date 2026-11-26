package com.betancourt.backend.dto.user;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class ClientProfileDTO {
    private LocalDate birthDate;
    @Size(max = 255) private String address;
    @Size(max = 150) private String emergencyContactName;
    @Size(max = 30) private String emergencyContactPhone;
    private String notes;

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getEmergencyContactName() { return emergencyContactName; }
    public void setEmergencyContactName(String emergencyContactName) { this.emergencyContactName = emergencyContactName; }
    public String getEmergencyContactPhone() { return emergencyContactPhone; }
    public void setEmergencyContactPhone(String emergencyContactPhone) { this.emergencyContactPhone = emergencyContactPhone; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}