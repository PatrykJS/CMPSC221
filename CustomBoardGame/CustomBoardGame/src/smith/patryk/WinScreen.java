package smith.patryk;

/**
 * @author Patryk Smith
 * 
 * Just a fun end screen animation. 
 */

public class WinScreen {
    String[] winner;
    String YOU_WON = " __     ______  _    _  __          ______  _   _ _ \n" +
           " \\ \\   / / __ \\| |  | | \\ \\        / / __ \\| \\ | | |\n" +
           "  \\ \\_/ | |  | | |  | |  \\ \\  /\\  / | |  | |  \\| | |\n" +
           "   \\   /| |  | | |  | |   \\ \\/  \\/ /| |  | | . ` | |\n" +
           "    | | | |__| | |__| |    \\  /\\  / | |__| | |\\  |_|\n" +
           "    |_|  \\____/ \\____/      \\/  \\/   \\____/|_| \\_(_)\n" +
           "                                                    \n" +
           "                                                    ";
    String OU_WONY = "   ____  _    _  __          ______  _   _ _  __     __\n" +
            "  / __ \\| |  | | \\ \\        / / __ \\| \\ | | | \\ \\   / /\n" +
            " | |  | | |  | |  \\ \\  /\\  / | |  | |  \\| | |  \\ \\_/ / \n" +
            " | |  | | |  | |   \\ \\/  \\/ /| |  | | . ` | |   \\   /  \n" +
            " | |__| | |__| |    \\  /\\  / | |__| | |\\  |_|    | |   \n" +
            "  \\____/ \\____/      \\/  \\/   \\____/|_| \\_(_)    |_|   \n" +
            "                                                       \n" +
            "                                                       ";
    String U_WONYO = "  _    _  __          ______  _   _ _  __     ______  \n" +
            " | |  | | \\ \\        / / __ \\| \\ | | | \\ \\   / / __ \\ \n" +
            " | |  | |  \\ \\  /\\  / | |  | |  \\| | |  \\ \\_/ | |  | |\n" +
            " | |  | |   \\ \\/  \\/ /| |  | | . ` | |   \\   /| |  | |\n" +
            " | |__| |    \\  /\\  / | |__| | |\\  |_|    | | | |__| |\n" +
            "  \\____/      \\/  \\/   \\____/|_| \\_(_)    |_|  \\____/ \n" +
            "                                                      \n" +
            "                                                      ";
    String _WONYOU = "  __          ______  _   _ _  __     ______  _    _ \n" +
            "  \\ \\        / / __ \\| \\ | | | \\ \\   / / __ \\| |  | |\n" +
            "   \\ \\  /\\  / | |  | |  \\| | |  \\ \\_/ | |  | | |  | |\n" +
            "    \\ \\/  \\/ /| |  | | . ` | |   \\   /| |  | | |  | |\n" +
            "     \\  /\\  / | |__| | |\\  |_|    | | | |__| | |__| |\n" +
            "      \\/  \\/   \\____/|_| \\_(_)    |_|  \\____/ \\____/ \n" +
            "                                                     \n" +
            "                                                     ";
    String WONYOU_ = " __          ______  _   _ _  __     ______  _    _ \n" +
            " \\ \\        / / __ \\| \\ | | | \\ \\   / / __ \\| |  | |\n" +
            "  \\ \\  /\\  / | |  | |  \\| | |  \\ \\_/ | |  | | |  | |\n" +
            "   \\ \\/  \\/ /| |  | | . ` | |   \\   /| |  | | |  | |\n" +
            "    \\  /\\  / | |__| | |\\  |_|    | | | |__| | |__| |\n" +
            "     \\/  \\/   \\____/|_| \\_(_)    |_|  \\____/ \\____/ \n" +
            "                                                    \n" +
            "                                                    ";
    String ONYOU_W = "   ____  _   _ _  __     ______  _    _  __          __\n" +
            "  / __ \\| \\ | | | \\ \\   / / __ \\| |  | | \\ \\        / /\n" +
            " | |  | |  \\| | |  \\ \\_/ | |  | | |  | |  \\ \\  /\\  / / \n" +
            " | |  | | . ` | |   \\   /| |  | | |  | |   \\ \\/  \\/ /  \n" +
            " | |__| | |\\  |_|    | | | |__| | |__| |    \\  /\\  /   \n" +
            "  \\____/|_| \\_(_)    |_|  \\____/ \\____/      \\/  \\/    \n" +
            "                                                       \n" +
            "                                                       ";
    String NYOU_WO = "  _   _ _  __     ______  _    _  __          ______  \n" +
            " | \\ | | | \\ \\   / / __ \\| |  | | \\ \\        / / __ \\ \n" +
            " |  \\| | |  \\ \\_/ | |  | | |  | |  \\ \\  /\\  / | |  | |\n" +
            " | . ` | |   \\   /| |  | | |  | |   \\ \\/  \\/ /| |  | |\n" +
            " | |\\  |_|    | | | |__| | |__| |    \\  /\\  / | |__| |\n" +
            " |_| \\_(_)    |_|  \\____/ \\____/      \\/  \\/   \\____/ \n" +
            "                                                      \n" +
            "                                                      ";
    
    WinScreen(){
        init();
    }
    
    public void init(){
        winner = new String[7];
        winner[0] = YOU_WON;
        winner[1] = OU_WONY;
        winner[2] = U_WONYO;
        winner[3] = _WONYOU;
        winner[4] = WONYOU_;
        winner[5] = ONYOU_W;
        winner[6] = NYOU_WO;
    }
    public String[] screen(){
        return winner;
    }    
}
