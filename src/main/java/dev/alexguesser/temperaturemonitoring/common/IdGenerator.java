package dev.alexguesser.temperaturemonitoring.common;

import java.util.Optional;
import java.util.UUID;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochRandomGenerator;

import io.hypersistence.tsid.TSID;

public class IdGenerator {

    private static final TimeBasedEpochRandomGenerator TIME_BASED_EPOCH_RANDOM_GENERATOR;
    private static final TSID.Factory TSID_FACTORY;

    static {
        Optional.ofNullable(System.getenv("tsid.node"))
                .ifPresent(
                        node -> System.setProperty("tsid.node", node));
        Optional.ofNullable(System.getenv("tsid.node.count"))
                .ifPresent(
                        nodeCount -> System.setProperty("tsid.node.count", nodeCount));
        TIME_BASED_EPOCH_RANDOM_GENERATOR = Generators.timeBasedEpochRandomGenerator();
        TSID_FACTORY = TSID.Factory.builder().build();
    }

    private IdGenerator() {
    }

    public static UUID generateTimeBasedUUID() {
        return TIME_BASED_EPOCH_RANDOM_GENERATOR.generate();
    }

    public static TSID generateTSID() {


        return TSID_FACTORY.generate();
    }
}
