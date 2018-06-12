package com.example.a201619060353.atividadeextra2.dados;

import java.util.Calendar;

public class Datas {
    private static Calendar now = Calendar.getInstance();
    private static String ano = String.valueOf(now.get(Calendar.YEAR));
    private static String mes = String.valueOf(now.get(Calendar.MONTH) + 1);
    public static String PAGAMENTO_FUNC = "5/"+ mes + "/" + ano;
}
