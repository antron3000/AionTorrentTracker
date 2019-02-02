package avm;

import org.aion.avm.api.ABIDecoder;
import org.aion.avm.api.Address;
import org.aion.avm.api.BlockchainRuntime;
import org.aion.avm.userlib.AionMap;

public class SampleContract {

    private static AionMap<Integer, String> map1 = new AionMap<>();

    private static Address owner;

    static {
        owner = BlockchainRuntime.getCaller();
    }

    public static byte[] main() {
        return ABIDecoder.decodeAndRunWithClass(SampleContract.class, BlockchainRuntime.getData());
    }

    public static void ownerSampleFunction() {
        BlockchainRuntime.require(BlockchainRuntime.getCaller().equals(owner));
    }

    public static int sum(int a, int b) {
        return a + b;
    }

    public static void mapPut(int key, String value) {
        map1.put(key, value);
    }

    public static String mapGet(int key) {
        return map1.get(key);
    }

    public static void logEvent() {
        BlockchainRuntime.log("data1".getBytes());
        BlockchainRuntime.log("topic1".getBytes(), "data2".getBytes());
    }
}
