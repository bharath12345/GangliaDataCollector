import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class RandomAccessFileTest {

    public static void main(String[] args) throws IOException {
        File f = new File("test");
        Random randomGenerator = new Random();
        if(f.exists()) {
            if(!f.delete()) {
                System.out.println("file not deleted.");
                return;
            }
        }
        if(!f.createNewFile()) {
            System.out.println("file not created.");
            return;
        }
        RandomAccessFile file = new RandomAccessFile("test", "rw");
        int pos = randomGenerator.nextInt(99999999); int seek = randomGenerator.nextInt(99999999);
        System.out.println("numbers are on position: " + pos);
        byte[] b = { 1, 1, 0};
        file.seek(pos);
        file.write(b);
        System.out.println("starting to read in loop, hit key to quit...");
        while(System.in.available() == 0) {
            byte[] res = new byte[3];
            file.seek(pos);
            file.read(res);
            file.seek(seek);
            seek = randomGenerator.nextInt(99999999);
            if(res[0] != 1 || res[1] != 1 || res[2] != 0) {
                throw new RuntimeException("wrong numbers read.");
            }
        }
    }
}
