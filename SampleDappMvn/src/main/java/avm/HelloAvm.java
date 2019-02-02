package avm;

import org.aion.avm.api.ABIDecoder;
import org.aion.avm.api.BlockchainRuntime;

public class HelloAvm
{
    public static void sayHello() {
        BlockchainRuntime.println("Hello Avm");
    }

    public static String greet(String name) {
        return "Hello 2Feb19 " + name;
    }

    public static byte[] main() {
        return ABIDecoder.decodeAndRunWithClass(HelloAvm.class, BlockchainRuntime.getData());
    }
}
