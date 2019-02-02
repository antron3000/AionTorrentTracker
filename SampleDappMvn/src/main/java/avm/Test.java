package avm;

public class Test {

    public static void main(String[] args) {
        String s = "magnet:?xt=urn:btih:3d381affbf1425ca6d03cb499941dae1ca73ba54&dn=KMSpico+12.3.24+FINAL+%2B+Portable+%28Office+and+Windows+10+Activato&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969&tr=udp%3A%2F%2Fzer0day.ch%3A1337&tr=udp%3A%2F%2Fopen.demonii.com%3A1337&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969&tr=udp%3A%2F%2Fexodus.desync.com%3A6969";
        String[] splittedDN;
        String[] splittedTR;
        String displayName;

        splittedDN = s.split("&dn=");
//
//        for ( int i=0; i<splittedDN.length; i++) {
//            System.out.println(splittedDN[i]);
//        }

        splittedTR = splittedDN[1].split("&tr=");

//        for ( int i=0; i<p.length; i++) {
//            System.out.println(splittedTR[i]);
//        }

        displayName = splittedTR[0];
        System.out.println(displayName);

    }
}
