package avm;

import org.aion.avm.api.ABIDecoder;
import org.aion.avm.api.BlockchainRuntime;

public class HelloWorld {

    public static void sayHello() {
        BlockchainRuntime.println("Hello World!");
    }

    public static byte[] main() {
        return ABIDecoder.decodeAndRunWithClass(HelloWorld.class, BlockchainRuntime.getData());
    }

}
