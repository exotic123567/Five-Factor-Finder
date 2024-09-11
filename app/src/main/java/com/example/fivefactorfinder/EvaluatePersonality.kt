package com.example.fivefactorfinder

import android.util.Log
import com.example.fivefactorfinder.Question
import kotlinx.serialization.encodeToString
import kotlin.math.pow

class EvaluatePersonality {
    var numofquestions = 120
    fun evaluate(questionsList: List<Question>,sex: String, age: Int): String {

        //Create the answer's list
        var qlist = mutableListOf<Int>()
        repeat(120) {index ->
            qlist.add(3)
        }
        numofquestions = questionsList.size
        for (i in 0 until numofquestions) {
            qlist.add(questionsList[i].answerValue)
        }

        //Create Score facet scales
        var ss = mutableListOf<Int>()
        repeat(120) {index ->
            ss.add(0)
        }
        var k = 0
        for (i in 0..30) {
            k=0
            for (j in 0 until 4) {
                ss[i] += qlist[i+k]
                k+=30
            }
        }

        //Number each facet set from 1-6
        var nf = mutableListOf<Int>()
        repeat(120) {index ->
            nf.add(0)
        }
        var ef = mutableListOf<Int>()
        repeat(120) {index ->
            ef.add(0)
        }
        var of = mutableListOf<Int>()
        repeat(120) {index ->
            of.add(0)
        }
        var af = mutableListOf<Int>()
        repeat(120) {index ->
            af.add(0)
        }
        var cf = mutableListOf<Int>()
        repeat(120) {index ->
            cf.add(0)
        }
        var j = 0
        for (i in 0 until 6) {
            nf[i] = ss[i+j]
            ef[i] = ss[i + j + 1]
            of[i] = ss[i + j + 2]
            af[i] = ss[i + j + 3]
            cf[i] = ss[i + j + 4]
            j = j + 4
        }

        // Score domain scales
        //         1       2        3        4        5        6
        var n = ss[0] + ss[5]  + ss[10] + ss[15] + ss[20] + ss[25]
        var e = ss[1] + ss[6]  + ss[11] + ss[16] + ss[21] + ss[26]
        var o = ss[2] + ss[7]  + ss[12] + ss[17] + ss[22] + ss[27]
        var a = ss[3] + ss[8]  + ss[13] + ss[18] + ss[23] + ss[28]
        var c = ss[4] + ss[9]  + ss[14] + ss[19] + ss[24] + ss[39]

        // Standardize scores
        var norm = listOf(
                0, 67.84, 80.70, 85.98, 81.98, 79.66, 15.83, 15.37, 12.37, 14.66, 14.49,
        11.72, 11.93, 10.58, 12.38, 11.67,  9.63,  3.76,  4.41,  4.25,  3.83,  3.25, 3.38,
        13.76, 12.23, 14.06, 11.54, 14.67, 14.41,  3.78,  4.17,  3.66,  3.15,  3.38, 3.68,
        16.68, 14.51, 14.52, 12.84, 15.47, 11.86,  2.96,  3.87,  3.31,  3.16,  3.50, 3.17,
        13.18, 14.85, 15.37, 12.73, 12.01, 13.96,  3.69,  3.44,  3.10,  4.05,  3.94, 3.35,
        15.31, 10.97, 15.22, 13.61, 12.35, 12.08,  2.55,  3.93,  2.92,  3.65,  3.24, 4.02) // Default normalization list
        var categoryofuser = "males less than 21 years of age"

        if ((sex == "Male" || sex =="male") && age < 21) {
            norm = listOf(
                    0, 67.84, 80.70, 85.98, 81.98, 79.66, 15.83, 15.37, 12.37, 14.66, 14.49,
            11.72, 11.93, 10.58, 12.38, 11.67,  9.63,  3.76,  4.41,  4.25,  3.83,  3.25, 3.38,
            13.76, 12.23, 14.06, 11.54, 14.67, 14.41,  3.78,  4.17,  3.66,  3.15,  3.38, 3.68,
            16.68, 14.51, 14.52, 12.84, 15.47, 11.86,  2.96,  3.87,  3.31,  3.16,  3.50, 3.17,
            13.18, 14.85, 15.37, 12.73, 12.01, 13.96,  3.69,  3.44,  3.10,  4.05,  3.94, 3.35,
            15.31, 10.97, 15.22, 13.61, 12.35, 12.08,  2.55,  3.93,  2.92,  3.65,  3.24, 4.02)
            categoryofuser = "males less than 21 years of age"
        } else if ((sex == "Male" || sex =="male") && age in 21..40) {
            norm = listOf(
                    0, 66.97, 78.90, 86.51, 84.22, 85.50, 16.48, 15.21, 12.65, 13.10, 14.27,
            11.44, 11.75, 10.37, 12.11, 12.18,  9.13,  3.76, 4.30,   4.12,  3.81, 3.52, 3.48,
            13.31, 11.34, 14.58, 12.07, 13.34, 14.30,  3.80, 3.99,   3.58,  3.23, 3.43, 3.53,
            15.94, 14.94, 14.60, 13.14, 16.11, 11.66,  3.18, 3.63,   3.19,  3.39, 3.25, 3.72,
            12.81, 15.93, 15.37, 14.58, 11.43, 13.77,  3.69, 3.18,   2.92,  3.70, 3.57, 3.29,
            15.80, 12.05, 15.68, 15.36, 13.27, 13.31,  2.44, 4.26,   2.76,  3.39, 3.31, 4.03)
            categoryofuser = "men between 21 and 40 years of age"
        } else if ((sex == "Male" || sex =="male") && age in 41..60) {
            norm = listOf(
                    0, 64.11, 77.06, 83.04, 88.33, 91.27, 16.04,  14.31, 13.05, 11.76, 13.35,
            10.79, 11.60,  9.78, 11.85, 11.24,  8.81,  3.56,  4.16,  3.94,  3.62,   3.55, 3.35,
            13.22, 10.45, 14.95, 12.27, 11.82, 14.32,  3.71,  3.68,  3.44,  3.30,   3.23, 3.29,
            14.65, 14.66, 14.76, 12.69, 15.40, 11.04,  3.35,  3.59,  3.02,  3.44,   3.43, 3.93,
            13.42, 16.94, 15.65, 15.66, 11.96, 14.21,  3.49,  2.83,  2.88,  3.33,   3.34, 3.17,
            16.19, 13.33, 16.56, 16.51, 14.05, 14.60,  2.25,  4.32,  2.50,  2.93,   3.13, 3.78)
            categoryofuser = "men between 41 and 60 years of age"
        } else if ((sex == "Male" || sex =="male") && age > 60) {
            norm = listOf(
                    0,  58.42, 79.73, 79.78, 90.20, 95.31, 15.48, 13.63, 12.21, 11.73, 11.99,
            9.81, 11.46,  8.18, 11.08,  9.91,  8.24,  3.54,  4.31,  3.59,  3.82,  3.36, 3.28,
            14.55, 11.19, 15.29, 12.81, 11.03, 15.02,  3.47,  3.58,  3.10,  3.25,  2.88, 3.16,
            14.06, 14.22, 14.34, 12.42, 14.61, 10.11,  3.13,  3.64,  2.90,  3.20,  3.89, 4.02,
            13.96, 17.74, 15.76, 16.18, 11.87, 14.00,  3.13,  2.39,  2.74,  3.41,  3.50, 3.11,
            16.32, 14.41, 17.54, 16.65, 14.98, 15.18,  2.31,  4.49,  2.30,  2.68,  2.76, 3.61)
            categoryofuser = "men over 60 years of age"
        } else if ((sex == "Female" || sex =="female") && age < 21) {
            norm = listOf(
                    0,     73.41, 84.26, 89.01, 89.14, 81.27, 15.61, 14.98, 11.84,  13.21, 14.38,
            13.31, 13.09, 11.05, 12.11, 12.48, 11.30,  3.62,  4.18,  4.20,  3.82,  3.30, 3.47,
            14.47, 13.12, 14.03, 12.67, 14.69, 15.34,  3.60,  4.13,  3.68,  3.09,  3.48, 3.42,
            16.86, 15.93, 16.02, 12.95, 15.06, 12.17,  2.89,  3.44,  2.95,  3.24,  3.51, 3.02,
            13.46, 16.11, 16.66, 13.73, 13.23, 15.70,  3.72,  2.94,  2.69,  4.14,  3.79, 2.84,
            15.30, 11.11, 15.62, 14.69, 12.73, 11.82,  2.54,  4.17,  2.76,  3.37,  3.19, 4.01)
            categoryofuser = "females less than 21 years of age"
        } else if ((sex == "Female" || sex =="female") && age in 21..40) {
            norm = listOf(
                    0,  72.14, 80.78, 88.25, 91.91, 87.57, 16.16, 14.64, 12.15, 11.39, 13.87,
            13.08, 12.72, 10.79, 12.20, 12.71, 10.69,  3.68,  4.13,  4.07,  3.79,  3.58, 3.64,
            14.05, 11.92, 14.25, 12.77, 12.84, 14.96,  3.66,  4.05,  3.61,  3.24,  3.53, 3.31,
            15.64, 15.97, 16.41, 12.84, 15.28, 12.06,  3.34,  3.30,  2.69,  3.44,  3.47, 3.46,
            13.15, 17.34, 16.81, 15.57, 12.98, 15.52,  3.71,  2.61,  2.53,  3.50,  3.57, 2.87,
            16.02, 12.67, 16.36, 16.11, 13.56, 12.91,  2.34,  4.51,  2.54,  3.05,  3.23, 4.18)
            categoryofuser = "women between 21 and 40 years of age"
        } else if ((sex == "Female" || sex =="female") && age in 41..60) {
            norm = listOf(
                    0, 67.38, 78.62, 86.15, 95.73, 93.45, 16.10, 14.19, 12.62, 9.84, 12.94,
            12.05, 11.19, 10.07, 12.07, 11.98, 10.07,  3.72,  4.03,  3.97, 3.73, 3.69, 3.56,
            14.10, 10.84, 14.51, 13.03, 11.08, 15.00,  3.72,  3.86,  3.50, 3.46, 3.42, 3.26,
            14.43, 16.00, 16.37, 12.58, 14.87, 11.85,  3.49,  3.20,  2.58, 3.45, 3.65, 3.74,
            13.79, 18.16, 17.04, 17.02, 13.41, 15.82,  3.52,  2.21,  2.40, 2.88, 3.30, 2.71,
            16.50, 13.68, 17.29, 17.16, 14.35, 14.41,  2.16,  4.51,  2.27, 2.73, 3.13, 3.86)
            categoryofuser = "women between 41 and 60 years of age"
        } else if ((sex == "Female" || sex =="female") && age > 60) {
            norm = listOf(
                    0,     63.48, 78.22, 81.56, 97.17, 96.44, 14.92, 12.73, 12.66, 9.52,  12.43,
            11.39, 10.52,  9.10, 12.00, 10.21,  9.87,  3.61,  3.82,  3.68, 3.61,  3.58, 3.44,
            14.85, 10.93, 14.19, 12.76, 10.08, 15.65,  3.43,  3.70,  3.64, 3.26,  3.20, 3.04,
            13.15, 15.95, 15.73, 11.80, 14.21, 10.81,  3.71,  3.12,  2.74, 3.26,  3.47, 3.89,
            14.19, 18.64, 17.13, 17.98, 13.58, 15.83,  3.39,  1.90,  2.18, 2.56,  3.38, 2.85,
            16.50, 15.15, 18.34, 17.19, 14.70, 15.11,  2.24,  4.07,  1.81, 2.49,  3.15, 3.66)
            categoryofuser = "women over 60 years of age"
        }

        var sn = (10 * (n - norm[1].toDouble()) / norm[6].toDouble()) + 50
        var se = (10 * (e - norm[2].toDouble()) / norm[7].toDouble()) + 50
        var so = (10 * (o - norm[3].toDouble()) / norm[8].toDouble()) + 50
        var sa = (10 * (a - norm[4].toDouble()) / norm[9].toDouble()) + 50
        var sc = (10 * (c - norm[5].toDouble()) / norm[10].toDouble()) + 50

        var snf = mutableListOf<Double>()
        repeat(120) {index ->
            snf.add(0.0)
        }
        var sef = mutableListOf<Double>()
        repeat(120) {index ->
            sef.add(0.0)
        }
        var sof = mutableListOf<Double>()
        repeat(120) {index ->
            sof.add(0.0)
        }
        var saf = mutableListOf<Double>()
        repeat(120) {index ->
            saf.add(0.0)
        }
        var scf = mutableListOf<Double>()
        repeat(120) {index ->
            scf.add(0.0)
        }

        for (i in 0 until 6) {
            snf[i] = 50 + (10 * (nf[i] - norm[i + 10].toDouble()) / norm[i + 16].toDouble())
            sef[i] = 50 + (10 * (ef[i] - norm[i + 22].toDouble()) / norm[i + 28].toDouble())
            sof[i] = 50 + (10 * (of[i] - norm[i + 34].toDouble()) / norm[i + 40].toDouble())
            saf[i] = 50 + (10 * (af[i] - norm[i + 46].toDouble()) / norm[i + 52].toDouble())
            scf[i] = 50 + (10 * (cf[i] - norm[i + 58].toDouble()) / norm[i + 64].toDouble())
        }

         // Cubic approximation for percentiles
        val const1 = 210.335958661391
        val const2 = 16.7379362643389
        val const3 = 0.405936512733332
        val const4 = 0.00270624341822222

        var snp = (const1 - (const2 * sn) + (const3 * sn.pow(2)) - (const4 * sn.pow(3))).toInt()
        var sep = (const1 - (const2 * se) + (const3 * se.pow(2)) - (const4 * se.pow(3))).toInt()
        var sop = (const1 - (const2 * so) + (const3 * so.pow(2)) - (const4 * so.pow(3))).toInt()
        var sap = (const1 - (const2 * sa) + (const3 * sa.pow(2)) - (const4 * sa.pow(3))).toInt()
        var scp = (const1 - (const2 * sc) + (const3 * sc.pow(2)) - (const4 * sc.pow(3))).toInt()

        if (sn < 32) snp = 1
        if (se < 32) sep = 1
        if (so < 32) sop = 1
        if (sa < 32) sap = 1
        if (sc < 32) scp = 1

        if (sn > 73) snp = 99
        if (se > 73) sep = 99
        if (so > 73) sop = 99
        if (sa > 73) sap = 99
        if (sc > 73) scp = 99

        // Create percentile scores and low, average, high labels for facets
        val items = 120
        val snfp = MutableList(items) { 0 }
        val sefp = MutableList(items) { 0 }
        val sofp = MutableList(items) { 0 }
        val safp = MutableList(items) { 0 }
        val scfp = MutableList(items) { 0 }
        val flev = MutableList(items) { 0.0 }
        var flevText = MutableList(items) { "low" }

        // Mapping for snf to snfp
        for (i in 0 until 6) {
            flev[i] = snf[i]
            flevText[i] = when {
                snf[i] < 45 -> "low"
                snf[i] in 45.0..55.0 -> "average"
                else -> "high"
            }
            snfp[i] = ((const1 - (const2 * snf[i]) + (const3 * snf[i].pow(2)) - (const4 * snf[i].pow(3))).toInt())
            if (snf[i] < 32) {
                snfp[i] = 1
            } else if (snf[i] > 73) {
                snfp[i] = 99
            }
        }
        // Mapping for sef to sefp
        for (i in 0 until 6) {
            flev[i + 6] = sef[i]
            flevText[i + 6] = when {
                sef[i] < 45 -> "low"
                sef[i] in 45.0..55.0 -> "average"
                else -> "high"
            }
            sefp[i] = ((const1 - (const2 * sef[i]) + (const3 * sef[i].pow(2)) - (const4 * sef[i].pow(3))).toInt())
            if (sef[i] < 32) {
                sefp[i] = 1
            } else if (sef[i] > 73) {
                sefp[i] = 99
            }
        }
        // Mapping for sof to sofp
        for (i in 0 until 6) {
            flev[i + 12] = sof[i]
            flevText[i + 12] = when {
                sof[i] < 45 -> "low"
                sof[i] in 45.0..55.0 -> "average"
                else -> "high"
            }
            sofp[i] = ((const1 - (const2 * sof[i]) + (const3 * sof[i].pow(2)) - (const4 * sof[i].pow(3))).toInt())
            if (sof[i] < 32) {
                sofp[i] = 1
            } else if (sof[i] > 73) {
                sofp[i] = 99
            }
        }
        // Mapping for saf to safp
        for (i in 0 until 6) {
            flev[i + 18] = saf[i]
            flevText[i + 18] = when {
                saf[i] < 45 -> "low"
                saf[i] in 45.0..55.0 -> "average"
                else -> "high"
            }
            safp[i] = ((const1 - (const2 * saf[i]) + (const3 * saf[i].pow(2)) - (const4 * saf[i].pow(3))).toInt())
            if (saf[i] < 32) {
                safp[i] = 1
            } else if (saf[i] > 73) {
                safp[i] = 99
            }
        }
        // Mapping for scf to scfp
        for (i in 0 until 6) {
            flev[i + 24] = scf[i]
            flevText[i + 24] = when {
                scf[i] < 45 -> "low"
                scf[i] in 45.0..55.0 -> "average"
                else -> "high"
            }
            scfp[i] = ((const1 - (const2 * scf[i]) + (const3 * scf[i].pow(2)) - (const4 * scf[i].pow(3))).toInt())
            if (scf[i] < 32) {
                scfp[i] = 1
            } else if (scf[i] > 73) {
                scfp[i] = 99
            }
        }

        val lo = 45
        val hi = 55

        //Results dumping
        val m = mutableMapOf<String, Int>()

        // EXTRAVERSION
        val extraversionLabels = listOf("EXTRAVERSION", "Friendliness", "Gregariousness", "Assertiveness", "Activity Level", "Excitement-Seeking", "Cheerfulness")
        m[extraversionLabels[0]] = sep
        for (i in 1 until extraversionLabels.size) {
            m[extraversionLabels[i]] = sefp[i]
        }

        // AGREEABLENESS
        val agreeablenessLabels = listOf("AGREEABLENESS", "Trust", "Morality", "Altruism", "Cooperation", "Modesty", "Sympathy")
        m[agreeablenessLabels[0]] = sap
        for (i in 1 until agreeablenessLabels.size) {
            m[agreeablenessLabels[i]] = safp[i]
        }

        // CONSCIENTIOUSNESS
        val conscientiousnessLabels = listOf("CONSCIENTIOUSNESS", "Self-Efficacy", "Orderliness", "Dutifulness", "Achievement-Striving", "Self-Discipline", "Cautiousness")
        m[conscientiousnessLabels[0]] = scp
        for (i in 1 until conscientiousnessLabels.size) {
            m[conscientiousnessLabels[i]] = scfp[i]
        }

        // NEUROTICISM
        val neuroticismLabels = listOf("NEUROTICISM", "Anxiety", "Anger", "Depression", "Self-Consciousness", "Immoderation", "Vulnerability")
        m[neuroticismLabels[0]] = snp
        for (i in 1 until neuroticismLabels.size) {
            m[neuroticismLabels[i]] = snfp[i]
        }

        // OPENNESS
        val opennessLabels = listOf("OPENNESS", "Imagination", "Artistic Interests", "Emotionality", "Adventurousness", "Intellect", "Liberalism")
        m[opennessLabels[0]] = sop
        for (i in 1 until opennessLabels.size) {
            m[opennessLabels[i]] = sofp[i]
        }

        // Convert the map to JSON
        Log.v("ShowMap","$m")
        val jsonString = kotlinx.serialization.json.Json.encodeToString(m)
        return jsonString
    }
}