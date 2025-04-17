package org.monstis.group.qalbms.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UzbekistanPhoneNumberValidator {

    public boolean isValidUzbekistanPhoneNumber(String phoneNumber) {
        String uzbekistanPhoneNumberRegex = "998(20|9[0-9])\\d{7}";

        if (phoneNumber.matches(uzbekistanPhoneNumberRegex)) {
            System.out.println("The phone number is valid.");
            return true;
        } else {
            System.out.println("The phone number is invalid.");
            return false;
        }
    }

}