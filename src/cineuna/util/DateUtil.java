/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 *
 * @author Chris
 */
public class DateUtil {
    
    /** The date pattern that is used for conversion. Change as you wish. */ 
    private static final String DATE_PATTERN = "dd/MM/yyyy"; 
 
    /** The date formatter. */ 
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN); 
    
    
    public DateUtil(){
        
    }
    
    //Retorna indice del dia de la semana
    public static int getDayOfWeek(){
        return LocalDate.now().getDayOfWeek().getValue();
    }
    
    //Retorna dias entre hoy y fecha concreta
    public static Integer daysUntil(LocalDate date){
        long daysBetween = ChronoUnit.DAYS.between(LocalDate.now(), date);
        return Math.toIntExact(daysBetween);
    }
    
    public static Integer daysUntil(Date date){
        return daysUntil(Date2LocalDate(date));
    }
    
    //Retorna dias entre dos fechas y fecha concreta
    public static Integer daysUntil2Dates(Date date1,Date date2){
        long daysBetween = ChronoUnit.DAYS.between(Date2LocalDate(date1), Date2LocalDate(date2));
        //System.out.println("días transcurridos: "+Math.toIntExact(daysBetween));
        return Math.toIntExact(daysBetween);
    }
    
    public static Integer hoursUntil(Integer h){
        LocalTime toTime = LocalTime.of(h, 0);
        Long difH = ChronoUnit.HOURS.between(LocalTime.now(), toTime);
        return Math.toIntExact(difH);
    }
    
    //Metodos conversores entre tipos String, Integer, LocalDate y Date
    public static String LocalDate2String(LocalDate date) { 
        if (date == null) { 
            return "INDEFINIDO"; 
        } 
        return DATE_FORMATTER.format(date); 
    } 
    
    public static String date2String(Date date) { 
        if (date == null) { 
            return "INDEFINIDO"; 
        } 
        return DATE_FORMATTER.format(Date2LocalDate(date)); 
    } 
 
    public static LocalDate String2LocalDate(String dateString) { 
        try { 
            return DATE_FORMATTER.parse(dateString, LocalDate::from); 
        } catch (DateTimeParseException e) { 
            return null; 
        } 
    }
    
    public static Date LocalDate2Date(LocalDate date){
        return java.sql.Date.valueOf(date);
    }
    
    public static LocalDate Date2LocalDate(Date date){
        LocalDate auxLD = LocalDate.of(date.getYear()+1900, date.getMonth()+1, date.getDate());
        return auxLD;
    }
    
    public static String Hour2String(Long hour){
        return Hour2String(Math.toIntExact(hour));
    }
    
    public static String Hour2String(Integer hour){
        if(hour<12){
            return String.valueOf(hour)+":00 am";
        } else {
            String strHour = null;
            switch(hour){
                case 12: strHour = "12:00 pm"; break;
                case 13: strHour = "1:00 pm"; break;
                case 14: strHour = "2:00 pm"; break;
                case 15: strHour = "3:00 pm"; break;
                case 16: strHour = "4:00 pm"; break;
                case 17: strHour = "5:00 pm"; break;
                case 18: strHour = "6:00 pm"; break;
                case 19: strHour = "7:00 pm"; break;
                case 20: strHour = "8:00 pm"; break;
                case 21: strHour = "9:00 pm"; break;
                case 22: strHour = "10:00 pm"; break;
                case 23: strHour = "11:00 pm"; break;
            }
            return strHour;
        }
    }
    
    /**
     * Resta minutos a un tiempo en formato HH:MM
     * Retornando solo las horas (HH) en formato 12h
     * @param HHMM
     * @return 
     */
    public static Integer getHH(Long HHMM){
        Long horas = HHMM / 100;
        horas = horas * 100;
        if(horas<12){
            return horas.intValue();
        }
        else{
            horas -= 12;
            return horas.intValue();
        }
    }
    
    /**
     * Resta las horas a un tiempo en formato HH:MM
     * Retornando solo los minutos (MM) en formato 12h
     * @param HHMM
     * @return 
     */
    public static Integer getMM(Long HHMM){
        Long min = HHMM - getHH(HHMM);
        return min.intValue();
    }
    
    /**
     * Calcula la cantidad de horas que hay en un tiempo dado en minutos
     * @param minutos
     * @return 
     */
    public static Integer getHoras(Long minutos){
        Long hh = minutos/60;
        return hh.intValue();
    }
    
    /**
     * Calcula el sobrante de minutos (sin contar horas) que hay en un tiempo dado en minutos
     * @param minutos
     * @return 
     */
    public static Integer getMinutos(Long minutos){
        Integer hh = getHoras(minutos);
        Integer mm = (Math.toIntExact(minutos)) - (hh * 60);
        return mm;
    }
    
    //Verifica si la fecha es valida
    public static boolean validDate(LocalDate date) {
        if(date != null){
            String dateString = LocalDate2String(date);
            // Try to toLocalDate the String. 
            return DateUtil.String2LocalDate(dateString) != null; 
        } else {
            return false;
        }
    } 
    
    public static Boolean after(LocalDate date){
        Date compare=LocalDate2Date(date);
        return compare.after(compare);
    }
    
    public static Boolean before(LocalDate date){
        Date compare=LocalDate2Date(date);
        return compare.before(compare);
    }
    
    public static Boolean equal(LocalDate date){
        Date compare=LocalDate2Date(date);
        return compare.equals(compare);
    }
}