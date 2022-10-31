package com.r2s.pte.common;

import java.util.ArrayList;
import java.util.List;

public class CodeCategory {
    public static final String CODE_RA = "RA";
    public static final String CODE_RS = "RS";
    public static final String CODE_DI = "DI";
    public static final String CODE_RL = "RL";
    public static final String CODE_AS= "AS";
    public static final String CODE_ESSAY = "ESSAY";
    public static final String CODE_SWT = "SWT";
    public static final String CODE_SST = "SST";
    public static final String CODE_DUALQ = "DUAL_Q";
    public static final String CODE_YN = "Y_N";
    public static final String CODE_OPENQ = "OPEN_Q";
    public static final String CODE_FIB = "FIB";
    public static final String CODE_MCQMA = "MCQ-MA";
    public static final String CODE_MCQSA = "MCQ-SA";
    public static final String CODE_SMW = "SMW";
    public static final String CODE_HCS = "HCS";
    public static final String CODE_DD = "DD";
    public static final String CODE_WFD = "WFD";
    public static final String CODE_ROD = "ROD";
    public static final String CODE_BAR = "BAR";
    public static final String CODE_LINE = "LINE";
    public static final String CODE_FLOW = "FLOW";
    public static final String CODE_PIE = "PIE";
    public static final String CODE_TABLE = "TABLE";
    public static final String CODE_MAP = "MAP";
    public static final String CODE_PIC = "PIC";
    public static final String CODE_COMB = "COMB";
    public static final List<String> AI_SPEECH_CATEGORIES;
    public static final List<String> AI_TEXT_CATEGORIES;
    static {
    	AI_SPEECH_CATEGORIES = new ArrayList<String>();
    	AI_TEXT_CATEGORIES = new ArrayList<String>();
    	AI_SPEECH_CATEGORIES.add(CODE_AS);
    	AI_SPEECH_CATEGORIES.add(CODE_DI);
    	AI_SPEECH_CATEGORIES.add(CODE_RA);
    	AI_SPEECH_CATEGORIES.add(CODE_RS);
    	AI_SPEECH_CATEGORIES.add(CODE_RL);
    	AI_SPEECH_CATEGORIES.add(CODE_BAR);
    	AI_SPEECH_CATEGORIES.add(CODE_LINE);
    	AI_SPEECH_CATEGORIES.add(CODE_COMB);
    	AI_SPEECH_CATEGORIES.add(CODE_FLOW);
    	AI_SPEECH_CATEGORIES.add(CODE_PIE);
    	AI_SPEECH_CATEGORIES.add(CODE_TABLE);
    	AI_SPEECH_CATEGORIES.add(CODE_MAP);
    	AI_SPEECH_CATEGORIES.add(CODE_PIC);
    	AI_TEXT_CATEGORIES.add(CODE_ESSAY);
    	AI_TEXT_CATEGORIES.add(CODE_SWT);
    	AI_TEXT_CATEGORIES.add(CODE_SST);
    }
}
