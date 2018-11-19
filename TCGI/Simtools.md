# TCGI.
	> It's not recommended to run simctl on Virtual Machines. Plz, Get Linux...



## Simtools

### LogIn on Simtools shells obtainet with `simctl <scenario> get <shell>`.
```
Press Enter.
user: root
pass: xxxx
```

### Problems and workarrounds on simctl
- By default, simctl does not catch the simulated shell resizes because that shells are running UML Kernel like. To be able to resize it and get a pretty looking ad formatted shell we do the following:
```
>Ctr + a
#Then we hold off.
>f
```
That runs stty commands to tell the shell the actual size parameters of the terminal.

- By default, no scrolling is enabled on UML Kernel shells of simclt. To be able to see the shell story, we do the following:
```
> Ctrl + a
#Then we hold off.
> ESC
#That enables the copy mode, which enables us to scroll up and down.
#To get off copy mode:
>ESC
```

### Simctl commands
**Never run a scenario as root.**
**Never run more than one simulation at the same time!**
- `simctl <scenario_name> start`: Runs on our machine the whole scenario envoirment.
- `simctl <scenario_name> status`: Prints the scenario simulation status on the cmd.
**Close the scenarios before turn off your PC**
- `simctl <scenario_name> stop`: Stops the running simulation.
- `simctl <scenario_name> sh`: Opens a sub-shell that enables us the simctlshell (on which we don't need to writte `simctl <scenario> command`, we can do: ` <scenario> command`).
- `simctl <scenario_name> vms`: Lists the current vms that are up on our envoirment and it's oppened shell sessions.
- `simctl <scenario_name> get`: Lists the current vms that are up on our scenario and tells us about if they are running or not.
- `simctl <scenario_name> get <machine_name> <shell_session_name>`: Opens us the shell session running on the machine. (It can be used as many times as we want **but not at the same time!!**) which means that closing the terminal window doesn't finnish the shell session.
- To kill simctl if you didn't rode any of the bold phrasses: 
```
simctl <scenario name> stop
simctl forcestop
exit
```
### Capturing traffic on our simulations.
- We can capture the traffic that goes through the switches by connecting to the taps (SimnetX's).
To do it we run Wireshark on them. But to be able to capture traffic on Wireshark, we must be root to entrer on promiscuous mode.
To let every user to capture traffic on wireshark without runnig sudo mode we do the following:
`sudo chmod +s /usr/bin/dumpcap`

If we don't want to do this, We will need to run: `sudo wireshark` every time we want to capture traffic.

#### simtools-captap to deploy fast a capturing scheme
- To start capturing traffic on more than one tap, we can use `simtools-captap` to **open one Wireshark instance for every tap we have on the scenario**:

##### simtools-captap commands
- `simtools-captap -k`: Kills all the Wireshark oppened interfaces.
- `simtools-captap -s -<final_interface>`: Opens one Wireshark instance for every tap on the scheme from Tap_0 to Tap<final_interface>.
- `simtools-captap -r` -<final_interface>: Restarts the Wireshark instances of every tap on the scheme from Tap_0 to Tap<final_interface>.
