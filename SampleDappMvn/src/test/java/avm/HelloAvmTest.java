package avm;

import org.aion.avm.api.ABIEncoder;
import org.aion.avm.api.Address;
import org.aion.avm.core.CommonAvmFactory;
import org.aion.avm.core.dappreading.JarBuilder;
import org.aion.avm.core.util.CodeAndArguments;
import org.aion.avm.core.util.Helpers;
import org.aion.avm.core.util.TestingHelper;
import org.aion.avm.userlib.AionMap;
import org.aion.kernel.*;
import org.aion.vm.api.interfaces.TransactionContext;
import org.aion.vm.api.interfaces.TransactionResult;
import org.aion.vm.api.interfaces.VirtualMachine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

/**
 * Unit test for HelloAvm
 */
public class HelloAvmTest {

    private KernelInterfaceImpl kernel;
    private VirtualMachine avm;
    private org.aion.vm.api.interfaces.Address deployer = KernelInterfaceImpl.PREMINED_ADDRESS;

    @Before
    public void setup() {
        this.kernel = new KernelInterfaceImpl();
        this.avm = CommonAvmFactory.buildAvmInstance(this.kernel);
    }


    @Test
    public void test_simpleContract() {
        Block block = new Block(new byte[32], 1, Helpers.randomAddress(), System.currentTimeMillis(), new byte[0]);
        byte[] jar = JarBuilder.buildJarForMainAndClasses(HelloAvm.class, AionMap.class);
        byte[] txData = new CodeAndArguments(jar, new byte[0]).encodeToBytes();

        // Deploy.
        long energyLimit = 10_000_000l;
        long energyPrice = 1l;
        Transaction create = Transaction.create(deployer, kernel.getNonce(deployer), BigInteger.ZERO, txData, energyLimit, energyPrice);
        TransactionResult createResult = avm.run(new TransactionContext[] {new TransactionContextImpl(create, block)})[0].get();
        Assert.assertEquals(AvmTransactionResult.Code.SUCCESS, createResult.getResultCode());
        Address contractAddr = TestingHelper.buildAddress(createResult.getReturnData());

        // We only want to check that we can call it without issue (it only produces STDOUT).
        callStatic(block, contractAddr, "sayHello");
    }


    private void callStatic(Block block, Address contractAddr, String methodName, Object... args) {
//        IInstrumentation instrumentation = newFakeInstrumentation();
//        InstrumentationHelpers.attachThread(instrumentation);
        long energyLimit = 1_000_000l;
        byte[] argData = ABIEncoder.encodeMethodArguments(methodName, args);
        Transaction call = Transaction.call(deployer, AvmAddress.wrap(contractAddr.unwrap()), kernel.getNonce(deployer), BigInteger.ZERO, argData, energyLimit, 1l);
        TransactionResult result = avm.run(new TransactionContext[] {new TransactionContextImpl(call, block)})[0].get();
//        InstrumentationHelpers.detachThread(instrumentation);
        Assert.assertEquals(AvmTransactionResult.Code.SUCCESS, result.getResultCode());
    }
}
