package org.monstis.group.qalbms.utils;

import org.springframework.stereotype.Component;

@Component
public class UzbekistanPhoneNumberValidator {

    public boolean isValidUzbekistanPhoneNumber(String phoneNumber) {
        String uzbekistanPhoneNumberRegex = "^(?:\\+998|998)(9[0-9]|6[0-9]|7[0-9]|8[0-9])[0-9]{7}$";

        if (phoneNumber.matches(uzbekistanPhoneNumberRegex)) {
            System.out.println("The phone number is valid.");
            return true;
        } else {
            System.out.println("The phone number is invalid.");
            return false;
        }
    }

}