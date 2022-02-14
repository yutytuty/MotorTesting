// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.spikes2212.command.genericsubsystem.MotoredGenericSubsystem;
import com.spikes2212.command.genericsubsystem.commands.MoveGenericSubsystem;
import com.spikes2212.dashboard.Namespace;
import com.spikes2212.dashboard.RootNamespace;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Robot extends TimedRobot {

    private final RootNamespace rootNamespace = new RootNamespace("Test Motors");
    private final Supplier<Double> speed = rootNamespace.addConstantDouble("Speed", 0.5);

    private int victorSPNum = 0, victorSPXNum = 1, talonSRXNum = 0, sparkMaxNum = 0;

    private final Map<Namespace, VictorSP> victorSPes = new HashMap<>();
    private final Map<Namespace, WPI_VictorSPX> victorSPXes = new HashMap<>();
    private final Map<Namespace, WPI_TalonSRX> talonSRXes = new HashMap<>();
    private final Map<Namespace, CANSparkMax> sparkMaxes = new HashMap<>();

    @Override
    public void robotInit() {
        for (int i = 0; i < Math.max(Math.max(victorSPNum, victorSPXNum), Math.max(talonSRXNum, sparkMaxNum)); i++) {
            if (i <= victorSPNum) {
                Namespace namespace = rootNamespace.addChild("VictorSP " + i);
                namespace.addConstantInt("port", -1);
                victorSPes.put(namespace, null);
            }

            if (i <= victorSPXNum) {
                Namespace namespace = rootNamespace.addChild("VictorSPX " + i);
                namespace.addConstantInt("port", -1);
                victorSPXes.put(namespace, null);
            }

            if (i <= talonSRXNum) {
                Namespace namespace = rootNamespace.addChild("TalonSRX " + i);
                namespace.addConstantInt("port", -1);
                talonSRXes.put(namespace, null);

            }

            if (i <= sparkMaxNum) {
                Namespace namespace = rootNamespace.addChild("SparkMax " + i);
                namespace.addConstantInt("port", -1);
                sparkMaxes.put(namespace, null);
            }
        }
    }

    @Override
    public void teleopInit() {
        for (Namespace namespace : victorSPes.keySet()) {
            victorSPes.put(namespace, new VictorSP((int) namespace.getNumber("port")));
            VictorSP victorSP = victorSPes.get(namespace);
            namespace.putData("Run " + namespace, new MoveGenericSubsystem(new MotoredGenericSubsystem("_", victorSP), 0.1));
        }
        for (Namespace namespace : victorSPXes.keySet()) {
            victorSPXes.put(namespace, new WPI_VictorSPX((int) namespace.getNumber("port")));
            WPI_VictorSPX victorSPX = victorSPXes.get(namespace);
            namespace.putData("Run " + namespace, new MoveGenericSubsystem(new MotoredGenericSubsystem("_", victorSPX), 0.1));
        }
        for (Namespace namespace : talonSRXes.keySet()) {
            talonSRXes.put(namespace, new WPI_TalonSRX((int) namespace.getNumber("port")));
            WPI_TalonSRX talon = talonSRXes.get(namespace);
            namespace.putData("Run " + namespace, new MoveGenericSubsystem(new MotoredGenericSubsystem("_", talon), 0.1));
        }
        for (Namespace namespace : sparkMaxes.keySet()) {
            sparkMaxes.put(namespace, new CANSparkMax((int) namespace.getNumber("port"), CANSparkMaxLowLevel.MotorType.kBrushless));
            CANSparkMax sparkMax = sparkMaxes.get(namespace);
            namespace.putData("Run " + namespace, new MoveGenericSubsystem(new MotoredGenericSubsystem("_", sparkMax), 0.1));
        }
    }

    @Override
    public void robotPeriodic() {
        rootNamespace.update();
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void autonomousInit() {
    }

    @Override
    public void autonomousPeriodic() {
    }

//    @Override
//    public void teleopInit() {
//    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {
    }
}
