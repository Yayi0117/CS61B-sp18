/** A client that uses the synthesizer package to replicate a plucked guitar string sound */
public class GuitarHero {
    private static final double CONCERT_A = 440.0;
    private static final double CONCERT_C = CONCERT_A * Math.pow(2, 3.0 / 12.0);

    private static double frequency(int i) {
        return CONCERT_A * Math.pow(2, (i - 24) / 12.0);
    }

    public static void main(String[] args) {
        /* create two guitar strings, for concert A and C */
        synthesizer.GuitarString[] heroStrings = new synthesizer.GuitarString[37];
        for (int i = 0; i < 37; i++) {
            heroStrings[i] = new synthesizer.GuitarString(frequency(i));
        }
        //synthesizer.GuitarString stringA = new synthesizer.GuitarString(CONCERT_A);
        //synthesizer.GuitarString stringC = new synthesizer.GuitarString(CONCERT_C);

        while (true) {
            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                switch (key) {
                    case 'q': heroStrings[0].pluck(); break;
                    case '2': heroStrings[1].pluck(); break;
                    case 'w': heroStrings[2].pluck(); break;
                    case 'e': heroStrings[3].pluck(); break;
                    case '4': heroStrings[4].pluck(); break;
                    case 'r': heroStrings[5].pluck(); break;
                    case '5': heroStrings[6].pluck(); break;
                    case 't': heroStrings[7].pluck(); break;
                    case 'y': heroStrings[8].pluck(); break;
                    case '7': heroStrings[9].pluck(); break;
                    case 'u': heroStrings[10].pluck(); break;
                    case '8': heroStrings[11].pluck(); break;
                    case 'i': heroStrings[12].pluck(); break;
                    case '9': heroStrings[13].pluck(); break;
                    case 'o': heroStrings[14].pluck(); break;
                    case 'p': heroStrings[15].pluck(); break;
                    case '-': heroStrings[16].pluck(); break;
                    case '[': heroStrings[17].pluck(); break;
                    case '=': heroStrings[18].pluck(); break;
                    case 'z': heroStrings[19].pluck(); break;
                    case 'x': heroStrings[20].pluck(); break;
                    case 'd': heroStrings[21].pluck(); break;
                    case 'c': heroStrings[22].pluck(); break;
                    case 'f': heroStrings[23].pluck(); break;
                    case 'v': heroStrings[24].pluck(); break;
                    case 'g': heroStrings[25].pluck(); break;
                    case 'b': heroStrings[26].pluck(); break;
                    case 'n': heroStrings[27].pluck(); break;
                    case 'j': heroStrings[28].pluck(); break;
                    case 'm': heroStrings[29].pluck(); break;
                    case 'k': heroStrings[30].pluck(); break;
                    case ',': heroStrings[31].pluck(); break;
                    case '.': heroStrings[32].pluck(); break;
                    case ';': heroStrings[33].pluck(); break;
                    case '/': heroStrings[34].pluck(); break;
                    case '\'': heroStrings[35].pluck(); break;
                    case ' ': heroStrings[36].pluck(); break;
                    default:  break;
                }
                /*
                if (key == 'a') {
                    stringA.pluck();
                } else if (key == 'c') {
                    stringC.pluck();
                }
                 */
            }

            /* compute the superposition of samples */
            double sample = 0.0;
            for (synthesizer.GuitarString string : heroStrings) {
                sample += string.sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (synthesizer.GuitarString string : heroStrings) {
                string.tic();
            }
        }
    }
}

