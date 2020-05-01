package com.e.bigmlexplorer.actionablemodels;
/**
 *  Predictor for Survived from model/5e73b2cf1efc9248fa002d0c
 *  Data set to predict survival on the Titanic, based on demographics and ticket information.
 *  Source
 *  The data source is from Encyclopedia Titanica[*]
 *
 *  [*]Encyclopedia Titanica: http://www.encyclopedia-titanica.org
 */
public class Survived {

    public static String predictSurvived(Double age, String classDept, Double fareToday, String joined) {
        if (classDept == null) {
            return "FALSE";
        }
        else if (classDept.equals("1st Class")) {
            if (fareToday == null) {
                return "TRUE";
            }
            else if (fareToday > 4165) {
                if (age == null) {
                    return "TRUE";
                }
                else if (age > 45.46667) {
                    if (age > 46.5) {
                        if (age > 62.5) {
                            return "FALSE";
                        }
                        else if (age <= 62.5) {
                            if (fareToday > 7050) {
                                if (age > 47.5) {
                                    if (age > 48.5) {
                                        if (fareToday > 9525) {
                                            return "TRUE";
                                        }
                                        else if (fareToday <= 9525) {
                                            return "FALSE";
                                        }
                                    }
                                    else if (age <= 48.5) {
                                        return "TRUE";
                                    }
                                }
                                else if (age <= 47.5) {
                                    return "FALSE";
                                }
                            }
                            else if (fareToday <= 7050) {
                                if (age > 49.5) {
                                    return "TRUE";
                                }
                                else if (age <= 49.5) {
                                    return "TRUE";
                                }
                            }
                        }
                    }
                    else if (age <= 46.5) {
                        return "FALSE";
                    }
                }
                else if (age <= 45.46667) {
                    if (fareToday > 4525) {
                        if (age > 30.5) {
                            if (fareToday > 7085) {
                                if (fareToday > 14500) {
                                    return "TRUE";
                                }
                                else if (fareToday <= 14500) {
                                    return "TRUE";
                                }
                            }
                            else if (fareToday <= 7085) {
                                return "TRUE";
                            }
                        }
                        else if (age <= 30.5) {
                            if (age > 23.5) {
                                if (fareToday > 5245) {
                                    if (fareToday > 6105) {
                                        return "TRUE";
                                    }
                                    else if (fareToday <= 6105) {
                                        return "TRUE";
                                    }
                                }
                                else if (fareToday <= 5245) {
                                    return "FALSE";
                                }
                            }
                            else if (age <= 23.5) {
                                if (fareToday > 5555) {
                                    if (fareToday > 5995) {
                                        if (fareToday > 7125) {
                                            if (fareToday > 7815) {
                                                return "TRUE";
                                            }
                                            else if (fareToday <= 7815) {
                                                return "FALSE";
                                            }
                                        }
                                        else if (fareToday <= 7125) {
                                            return "TRUE";
                                        }
                                    }
                                    else if (fareToday <= 5995) {
                                        return "FALSE";
                                    }
                                }
                                else if (fareToday <= 5555) {
                                    return "TRUE";
                                }
                            }
                        }
                    }
                    else if (fareToday <= 4525) {
                        return "TRUE";
                    }
                }
            }
            else if (fareToday <= 4165) {
                if (age == null) {
                    return "FALSE";
                }
                else if (age > 53.5) {
                    if (fareToday > 1995) {
                        if (age > 61.5) {
                            return "FALSE";
                        }
                        else if (age <= 61.5) {
                            return "FALSE";
                        }
                    }
                    else if (fareToday <= 1995) {
                        return "TRUE";
                    }
                }
                else if (age <= 53.5) {
                    if (age > 28.5) {
                        if (age > 50.5) {
                            return "TRUE";
                        }
                        else if (age <= 50.5) {
                            if (fareToday > 4040) {
                                return "TRUE";
                            }
                            else if (fareToday <= 4040) {
                                if (fareToday > 3080) {
                                    return "FALSE";
                                }
                                else if (fareToday <= 3080) {
                                    if (fareToday > 1985) {
                                        if (fareToday > 2005) {
                                            if (fareToday > 2020) {
                                                if (fareToday > 2040) {
                                                    if (age > 42.5) {
                                                        if (fareToday > 2545) {
                                                            return "FALSE";
                                                        }
                                                        else if (fareToday <= 2545) {
                                                            if (age > 49) {
                                                                return "FALSE";
                                                            }
                                                            else if (age <= 49) {
                                                                return "TRUE";
                                                            }
                                                        }
                                                    }
                                                    else if (age <= 42.5) {
                                                        if (age > 40.5) {
                                                            return "FALSE";
                                                        }
                                                        else if (age <= 40.5) {
                                                            if (fareToday > 2095) {
                                                                if (fareToday > 2340) {
                                                                    return "TRUE";
                                                                }
                                                                else if (fareToday <= 2340) {
                                                                    return "FALSE";
                                                                }
                                                            }
                                                            else if (fareToday <= 2095) {
                                                                return "TRUE";
                                                            }
                                                        }
                                                    }
                                                }
                                                else if (fareToday <= 2040) {
                                                    return "TRUE";
                                                }
                                            }
                                            else if (fareToday <= 2020) {
                                                return "FALSE";
                                            }
                                        }
                                        else if (fareToday <= 2005) {
                                            return "TRUE";
                                        }
                                    }
                                    else if (fareToday <= 1985) {
                                        return "FALSE";
                                    }
                                }
                            }
                        }
                    }
                    else if (age <= 28.5) {
                        return "TRUE";
                    }
                }
            }
        }
        else if (!classDept.equals("1st Class")) {
            if (classDept.equals("Deck")) {
                if (age == null) {
                    return "TRUE";
                }
                else if (age > 55) {
                    return "FALSE";
                }
                else if (age <= 55) {
                    if (age > 31.5) {
                        if (age > 34.5) {
                            if (age > 39.5) {
                                return "TRUE";
                            }
                            else if (age <= 39.5) {
                                if (age > 38.5) {
                                    return "FALSE";
                                }
                                else if (age <= 38.5) {
                                    return "TRUE";
                                }
                            }
                        }
                        else if (age <= 34.5) {
                            return "TRUE";
                        }
                    }
                    else if (age <= 31.5) {
                        if (age > 21.5) {
                            if (age > 29.5) {
                                return "FALSE";
                            }
                            else if (age <= 29.5) {
                                if (age > 26.5) {
                                    return "TRUE";
                                }
                                else if (age <= 26.5) {
                                    if (age > 25.5) {
                                        return "FALSE";
                                    }
                                    else if (age <= 25.5) {
                                        return "TRUE";
                                    }
                                }
                            }
                        }
                        else if (age <= 21.5) {
                            return "FALSE";
                        }
                    }
                }
            }
            else if (!classDept.equals("Deck")) {
                if (classDept.equals("2nd Class")) {
                    if (age == null) {
                        return "FALSE";
                    }
                    else if (age > 13.46667) {
                        if (fareToday == null) {
                            return "FALSE";
                        }
                        else if (fareToday > 5350) {
                            return "FALSE";
                        }
                        else if (fareToday <= 5350) {
                            if (fareToday > 1055) {
                                if (fareToday > 1075) {
                                    if (age > 25.58929) {
                                        if (fareToday > 1210) {
                                            if (age > 59.5) {
                                                return "FALSE";
                                            }
                                            else if (age <= 59.5) {
                                                if (age > 55.5) {
                                                    return "TRUE";
                                                }
                                                else if (age <= 55.5) {
                                                    if (fareToday > 2190) {
                                                        if (joined == null) {
                                                            return "TRUE";
                                                        }
                                                        else if (joined.equals("Southampton")) {
                                                            return "TRUE";
                                                        }
                                                        else if (!joined.equals("Southampton")) {
                                                            return "FALSE";
                                                        }
                                                    }
                                                    else if (fareToday <= 2190) {
                                                        if (fareToday > 1230) {
                                                            if (age > 27.5) {
                                                                if (age > 37.5) {
                                                                    if (fareToday > 1565) {
                                                                        return "FALSE";
                                                                    }
                                                                    else if (fareToday <= 1565) {
                                                                        return "TRUE";
                                                                    }
                                                                }
                                                                else if (age <= 37.5) {
                                                                    if (age > 36.5) {
                                                                        return "TRUE";
                                                                    }
                                                                    else if (age <= 36.5) {
                                                                        if (fareToday > 1430) {
                                                                            if (fareToday > 1930) {
                                                                                return "FALSE";
                                                                            }
                                                                            else if (fareToday <= 1930) {
                                                                                if (age > 33.5) {
                                                                                    return "TRUE";
                                                                                }
                                                                                else if (age <= 33.5) {
                                                                                    return "FALSE";
                                                                                }
                                                                            }
                                                                        }
                                                                        else if (fareToday <= 1430) {
                                                                            return "FALSE";
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            else if (age <= 27.5) {
                                                                return "FALSE";
                                                            }
                                                        }
                                                        else if (fareToday <= 1230) {
                                                            return "TRUE";
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        else if (fareToday <= 1210) {
                                            return "FALSE";
                                        }
                                    }
                                    else if (age <= 25.58929) {
                                        if (age > 24.5) {
                                            return "FALSE";
                                        }
                                        else if (age <= 24.5) {
                                            if (fareToday > 2375) {
                                                if (fareToday > 2635) {
                                                    return "TRUE";
                                                }
                                                else if (fareToday <= 2635) {
                                                    return "FALSE";
                                                }
                                            }
                                            else if (fareToday <= 2375) {
                                                return "TRUE";
                                            }
                                        }
                                    }
                                }
                                else if (fareToday <= 1075) {
                                    return "TRUE";
                                }
                            }
                            else if (fareToday <= 1055) {
                                if (joined == null) {
                                    return "FALSE";
                                }
                                else if (joined.equals("Cherbourg")) {
                                    return "TRUE";
                                }
                                else if (!joined.equals("Cherbourg")) {
                                    if (age > 50.5) {
                                        return "FALSE";
                                    }
                                    else if (age <= 50.5) {
                                        if (age > 33.5) {
                                            if (age > 34.5) {
                                                if (age > 35.5) {
                                                    if (fareToday > 1020) {
                                                        return "TRUE";
                                                    }
                                                    else if (fareToday <= 1020) {
                                                        if (joined.equals("Southampton")) {
                                                            return "FALSE";
                                                        }
                                                        else if (!joined.equals("Southampton")) {
                                                            return "TRUE";
                                                        }
                                                    }
                                                }
                                                else if (age <= 35.5) {
                                                    return "FALSE";
                                                }
                                            }
                                            else if (age <= 34.5) {
                                                return "TRUE";
                                            }
                                        }
                                        else if (age <= 33.5) {
                                            if (fareToday > 708) {
                                                if (age > 22.5) {
                                                    if (age > 25.5) {
                                                        if (fareToday > 951) {
                                                            if (fareToday > 988) {
                                                                return "FALSE";
                                                            }
                                                            else if (fareToday <= 988) {
                                                                return "TRUE";
                                                            }
                                                        }
                                                        else if (fareToday <= 951) {
                                                            return "FALSE";
                                                        }
                                                    }
                                                    else if (age <= 25.5) {
                                                        return "FALSE";
                                                    }
                                                }
                                                else if (age <= 22.5) {
                                                    if (age > 21.5) {
                                                        return "TRUE";
                                                    }
                                                    else if (age <= 21.5) {
                                                        return "FALSE";
                                                    }
                                                }
                                            }
                                            else if (fareToday <= 708) {
                                                return "TRUE";
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else if (age <= 13.46667) {
                        return "TRUE";
                    }
                }
                else if (!classDept.equals("2nd Class")) {
                    if (classDept.equals("A la Carte")) {
                        if (age == null) {
                            return "FALSE";
                        }
                        else if (age > 27.5) {
                            return "FALSE";
                        }
                        else if (age <= 27.5) {
                            if (age > 24.5) {
                                return "FALSE";
                            }
                            else if (age <= 24.5) {
                                return "FALSE";
                            }
                        }
                    }
                    else if (!classDept.equals("A la Carte")) {
                        if (age == null) {
                            return "FALSE";
                        }
                        else if (age > 5.4) {
                            if (joined == null) {
                                return "FALSE";
                            }
                            else if (joined.equals("Queenstown")) {
                                if (age > 34.5) {
                                    return "FALSE";
                                }
                                else if (age <= 34.5) {
                                    if (fareToday == null) {
                                        return "FALSE";
                                    }
                                    else if (fareToday > 533) {
                                        if (fareToday > 2055) {
                                            return "FALSE";
                                        }
                                        else if (fareToday <= 2055) {
                                            if (fareToday > 599) {
                                                if (fareToday > 603) {
                                                    if (age > 16.5) {
                                                        if (age > 21.5) {
                                                            return "TRUE";
                                                        }
                                                        else if (age <= 21.5) {
                                                            return "FALSE";
                                                        }
                                                    }
                                                    else if (age <= 16.5) {
                                                        return "TRUE";
                                                    }
                                                }
                                                else if (fareToday <= 603) {
                                                    return "TRUE";
                                                }
                                            }
                                            else if (fareToday <= 599) {
                                                if (age > 30.5) {
                                                    return "FALSE";
                                                }
                                                else if (age <= 30.5) {
                                                    if (age > 19.5) {
                                                        if (fareToday > 548) {
                                                            if (fareToday > 593) {
                                                                if (age > 23.5) {
                                                                    if (fareToday > 597) {
                                                                        return "FALSE";
                                                                    }
                                                                    else if (fareToday <= 597) {
                                                                        return "TRUE";
                                                                    }
                                                                }
                                                                else if (age <= 23.5) {
                                                                    if (age > 20.5) {
                                                                        return "FALSE";
                                                                    }
                                                                    else if (age <= 20.5) {
                                                                        return "FALSE";
                                                                    }
                                                                }
                                                            }
                                                            else if (fareToday <= 593) {
                                                                return "FALSE";
                                                            }
                                                        }
                                                        else if (fareToday <= 548) {
                                                            return "TRUE";
                                                        }
                                                    }
                                                    else if (age <= 19.5) {
                                                        return "TRUE";
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else if (fareToday <= 533) {
                                        return "FALSE";
                                    }
                                }
                            }
                            else if (!joined.equals("Queenstown")) {
                                if (age > 33.45935) {
                                    if (age > 47.5) {
                                        if (age > 50.5) {
                                            if (classDept.equals("Victualling")) {
                                                if (age > 56.5) {
                                                    return "TRUE";
                                                }
                                                else if (age <= 56.5) {
                                                    return "FALSE";
                                                }
                                            }
                                            else if (!classDept.equals("Victualling")) {
                                                return "FALSE";
                                            }
                                        }
                                        else if (age <= 50.5) {
                                            return "FALSE";
                                        }
                                    }
                                    else if (age <= 47.5) {
                                        if (age > 43.5) {
                                            if (classDept.equals("Engine")) {
                                                return "FALSE";
                                            }
                                            else if (!classDept.equals("Engine")) {
                                                if (fareToday == null) {
                                                    return "FALSE";
                                                }
                                                else if (fareToday > 1105) {
                                                    return "FALSE";
                                                }
                                                else if (fareToday <= 1105) {
                                                    if (fareToday > 611) {
                                                        return "TRUE";
                                                    }
                                                    else if (fareToday <= 611) {
                                                        return "FALSE";
                                                    }
                                                }
                                            }
                                        }
                                        else if (age <= 43.5) {
                                            if (fareToday == null) {
                                                return "FALSE";
                                            }
                                            else if (fareToday > 1215) {
                                                if (age > 39.5) {
                                                    return "FALSE";
                                                }
                                                else if (age <= 39.5) {
                                                    if (fareToday > 1685) {
                                                        return "FALSE";
                                                    }
                                                    else if (fareToday <= 1685) {
                                                        return "TRUE";
                                                    }
                                                }
                                            }
                                            else if (fareToday <= 1215) {
                                                if (joined.equals("Southampton")) {
                                                    if (fareToday > 617) {
                                                        return "FALSE";
                                                    }
                                                    else if (fareToday <= 617) {
                                                        if (age > 34.5) {
                                                            return "FALSE";
                                                        }
                                                        else if (age <= 34.5) {
                                                            return "TRUE";
                                                        }
                                                    }
                                                }
                                                else if (!joined.equals("Southampton")) {
                                                    return "FALSE";
                                                }
                                            }
                                        }
                                    }
                                }
                                else if (age <= 33.45935) {
                                    if (fareToday == null) {
                                        return "FALSE";
                                    }
                                    else if (fareToday > 1770) {
                                        if (age > 31) {
                                            return "TRUE";
                                        }
                                        else if (age <= 31) {
                                            return "FALSE";
                                        }
                                    }
                                    else if (fareToday <= 1770) {
                                        if (fareToday > 837) {
                                            if (fareToday > 1011) {
                                                if (fareToday > 1150) {
                                                    if (fareToday > 1235) {
                                                        if (age > 21.5) {
                                                            if (joined.equals("Southampton")) {
                                                                if (age > 24.5) {
                                                                    return "FALSE";
                                                                }
                                                                else if (age <= 24.5) {
                                                                    return "TRUE";
                                                                }
                                                            }
                                                            else if (!joined.equals("Southampton")) {
                                                                return "TRUE";
                                                            }
                                                        }
                                                        else if (age <= 21.5) {
                                                            return "FALSE";
                                                        }
                                                    }
                                                    else if (fareToday <= 1235) {
                                                        if (age > 27.5) {
                                                            return "FALSE";
                                                        }
                                                        else if (age <= 27.5) {
                                                            return "TRUE";
                                                        }
                                                    }
                                                }
                                                else if (fareToday <= 1150) {
                                                    return "FALSE";
                                                }
                                            }
                                            else if (fareToday <= 1011) {
                                                return "TRUE";
                                            }
                                        }
                                        else if (fareToday <= 837) {
                                            if (fareToday > 603) {
                                                if (fareToday > 611) {
                                                    if (age > 18.5) {
                                                        if (age > 25.5) {
                                                            if (age > 32.5) {
                                                                return "FALSE";
                                                            }
                                                            else if (age <= 32.5) {
                                                                if (fareToday > 771) {
                                                                    return "FALSE";
                                                                }
                                                                else if (fareToday <= 771) {
                                                                    if (age > 26.5) {
                                                                        if (age > 28.5) {
                                                                            if (fareToday > 733) {
                                                                                return "TRUE";
                                                                            }
                                                                            else if (fareToday <= 733) {
                                                                                return "FALSE";
                                                                            }
                                                                        }
                                                                        else if (age <= 28.5) {
                                                                            return "FALSE";
                                                                        }
                                                                    }
                                                                    else if (age <= 26.5) {
                                                                        return "FALSE";
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        else if (age <= 25.5) {
                                                            if (age > 19.5) {
                                                                if (age > 20.5) {
                                                                    if (fareToday > 687) {
                                                                        if (fareToday > 693) {
                                                                            return "FALSE";
                                                                        }
                                                                        else if (fareToday <= 693) {
                                                                            return "TRUE";
                                                                        }
                                                                    }
                                                                    else if (fareToday <= 687) {
                                                                        return "FALSE";
                                                                    }
                                                                }
                                                                else if (age <= 20.5) {
                                                                    if (fareToday > 617) {
                                                                        return "FALSE";
                                                                    }
                                                                    else if (fareToday <= 617) {
                                                                        return "TRUE";
                                                                    }
                                                                }
                                                            }
                                                            else if (age <= 19.5) {
                                                                return "FALSE";
                                                            }
                                                        }
                                                    }
                                                    else if (age <= 18.5) {
                                                        if (age > 17.5) {
                                                            return "TRUE";
                                                        }
                                                        else if (age <= 17.5) {
                                                            if (fareToday > 617) {
                                                                if (age > 14.5) {
                                                                    return "FALSE";
                                                                }
                                                                else if (age <= 14.5) {
                                                                    return "TRUE";
                                                                }
                                                            }
                                                            else if (fareToday <= 617) {
                                                                return "TRUE";
                                                            }
                                                        }
                                                    }
                                                }
                                                else if (fareToday <= 611) {
                                                    if (fareToday > 607) {
                                                        return "FALSE";
                                                    }
                                                    else if (fareToday <= 607) {
                                                        return "FALSE";
                                                    }
                                                }
                                            }
                                            else if (fareToday <= 603) {
                                                if (fareToday > 584) {
                                                    if (age > 20) {
                                                        if (fareToday > 599) {
                                                            if (age > 21.5) {
                                                                if (age > 32.5) {
                                                                    return "FALSE";
                                                                }
                                                                else if (age <= 32.5) {
                                                                    if (age > 31.5) {
                                                                        return "TRUE";
                                                                    }
                                                                    else if (age <= 31.5) {
                                                                        return "FALSE";
                                                                    }
                                                                }
                                                            }
                                                            else if (age <= 21.5) {
                                                                return "TRUE";
                                                            }
                                                        }
                                                        else if (fareToday <= 599) {
                                                            return "TRUE";
                                                        }
                                                    }
                                                    else if (age <= 20) {
                                                        return "FALSE";
                                                    }
                                                }
                                                else if (fareToday <= 584) {
                                                    if (age > 27.5) {
                                                        return "FALSE";
                                                    }
                                                    else if (age <= 27.5) {
                                                        if (age > 15.5) {
                                                            if (age > 21.5) {
                                                                if (fareToday > 541) {
                                                                    if (fareToday > 550) {
                                                                        if (fareToday > 554) {
                                                                            if (age > 24.5) {
                                                                                return "FALSE";
                                                                            }
                                                                            else if (age <= 24.5) {
                                                                                if (fareToday > 582) {
                                                                                    return "TRUE";
                                                                                }
                                                                                else if (fareToday <= 582) {
                                                                                    return "FALSE";
                                                                                }
                                                                            }
                                                                        }
                                                                        else if (fareToday <= 554) {
                                                                            return "TRUE";
                                                                        }
                                                                    }
                                                                    else if (fareToday <= 550) {
                                                                        return "FALSE";
                                                                    }
                                                                }
                                                                else if (fareToday <= 541) {
                                                                    return "TRUE";
                                                                }
                                                            }
                                                            else if (age <= 21.5) {
                                                                if (age > 20.5) {
                                                                    return "FALSE";
                                                                }
                                                                else if (age <= 20.5) {
                                                                    if (fareToday > 520) {
                                                                        return "FALSE";
                                                                    }
                                                                    else if (fareToday <= 520) {
                                                                        return "FALSE";
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        else if (age <= 15.5) {
                                                            return "TRUE";
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        else if (age <= 5.4) {
                            if (joined == null) {
                                return "TRUE";
                            }
                            else if (joined.equals("Cherbourg")) {
                                return "TRUE";
                            }
                            else if (!joined.equals("Cherbourg")) {
                                if (fareToday == null) {
                                    return "FALSE";
                                }
                                else if (fareToday > 1610) {
                                    return "FALSE";
                                }
                                else if (fareToday <= 1610) {
                                    return "TRUE";
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}