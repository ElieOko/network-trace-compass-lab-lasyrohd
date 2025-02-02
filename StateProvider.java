package org.eclipse.tracecompass.incubator.internal.kernel.analysts.core.analysis;

import java.util.Objects;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.tracecompass.ctf.core.trace.ICTFStream;
import org.eclipse.tracecompass.statesystem.core.ITmfStateSystemBuilder;
import org.eclipse.tracecompass.tmf.core.event.ITmfEvent;
import org.eclipse.tracecompass.tmf.core.event.aspect.TmfCpuAspect;
import org.eclipse.tracecompass.tmf.core.statesystem.AbstractTmfStateProvider;
import org.eclipse.tracecompass.tmf.core.statesystem.ITmfStateProvider;
import org.eclipse.tracecompass.tmf.core.trace.ITmfTrace;
import org.eclipse.tracecompass.tmf.core.trace.TmfTraceUtils;

/**
 * An example of a simple state provider for a simple state system analysis
 *
 * This module is also in the developer documentation of Trace Compass. If it is
 * modified here, the doc should also be updated.
 *
 * @author Alexandre Montplaisir
 * @author Geneviève Bastien
 */
public class StateProvider extends AbstractTmfStateProvider {

    private static final @NonNull String PROVIDER_ID = "org.eclipse.tracecompass.Student.state.provider"; //$NON-NLS-1$
    private static final int VERSION = 0;
    static  Map<Integer, SocketParams> maps = new HashMap<>();
    static SocketParams socketObj = new SocketParams();
    private static final String e_entry_socket       = "syscall_entry_socket";//$NON-NLS-1$
    private static final String e_entry_write        = "syscall_entry_write";//$NON-NLS-1$
    private static final String e_exit_write         = "syscall_exit_write";//$NON-NLS-1$
    private static final String e_entry_read         = "syscall_entry_read";//$NON-NLS-1$
    private static final String e_exit_read          = "syscall_exit_read";//$NON-NLS-1$
    private static final String e_exit_socket        = "syscall_exit_socket";//$NON-NLS-1$
    private static final String e_entry_bind         = "syscall_entry_bind";//$NON-NLS-1$
    private static final String e_exit_bind          = "syscall_exit_bind";//$NON-NLS-1$
    private static final String e_entry_listen       = "syscall_entry_listen";//$NON-NLS-1$
    private static final String e_exit_listen        = "syscall_exit_listen";//$NON-NLS-1$
    private static final String e_entry_accept       = "syscall_entry_accept";//$NON-NLS-1$
    private static final String e_exit_accept        = "syscall_exit_accept";//$NON-NLS-1$
    private static final String e_entry_accept4      = "syscall_entry_accept4";//$NON-NLS-1$
    private static final String e_exit_accept4       = "syscall_exit_accept4";//$NON-NLS-1$
    private static final String e_entry_connect      = "syscall_entry_connect";//$NON-NLS-1$
    private static final String e_exit_connect       = "syscall_exit_connect";//$NON-NLS-1$
    private static final String e_entry_getsockname  = "syscall_entry_getsockname";//$NON-NLS-1$
    private static final String e_exit_getsockname   = "syscall_exit_getsockname";//$NON-NLS-1$
    private static final String e_entry_setsockopt   = "syscall_entry_setsockopt";//$NON-NLS-1$
    private static final String e_exit_setsockopt    = "syscall_exit_setsockopt";//$NON-NLS-1$
    private static final String e_entry_getsockopt   = "syscall_entry_getsockopt";//$NON-NLS-1$
    private static final String e_exit_getsockopt    = "syscall_exit_getsockopt";//$NON-NLS-1$
    private static final String e_entry_sendmsg      = "syscall_entry_sendmsg";//$NON-NLS-1$
    private static final String e_exit_sendmsg       = "syscall_exit_sendmsg";//$NON-NLS-1$
    private static final String e_entry_recvmsg      = "syscall_entry_recvmsg";//$NON-NLS-1$
    private static final String e_exit_recvmsg      = "syscall_exit_recvmsg";//$NON-NLS-1$
    private static final String e_entry_close        = "syscall_entry_close";//$NON-NLS-1$
    private static final String e_exit_close         = "syscall_exit_close";//$NON-NLS-1$
    private static final String e_entry_shutdown     = "syscall_entry_shutdown";//$NON-NLS-1$
    private static final String e_exit_shutdown      = "syscall_exit_shutdown";//$NON-NLS-1$
    private static final String e_exit_fcntl         = "syscall_exit_fcntl";//$NON-NLS-1$
    private static final String e_entry_fcntl        = "syscall_entry_fcntl";//$NON-NLS-1$

    /**
     * Constructor
     *
     * @param trace
     *            The trace for this state provider
     */
    public StateProvider(@NonNull ITmfTrace trace) {
        super(trace, PROVIDER_ID);
    }

    @Override
    public int getVersion() {
        return VERSION;
    }

    @Override
    public @NonNull ITmfStateProvider getNewInstance() {
        return new StateProvider(getTrace());
    }

    @Override
    protected void eventHandle(ITmfEvent event) {

        /**
         * Do what needs to be done with this event, here is an example that
         * updates the CPU state and TID after a sched_switch
         *
         */

        SocketService socketConnexion = new SocketService();
        switch(event.getName()) {
        case "net_dev_queue":{
            socketConnexion.netdev(event,getStateSystemBuilder());
            break;
        }
//        case e_entry_socket,e_exit_socket:{
//            socketObj = socketConnexion.socket(event,getStateSystemBuilder(),true);
//            if(socketObj.cpu != 0) {
//                System.out.println("***MY-CPU->"+socketObj.cpu+" MY -> FD"+socketObj.fd);
//                maps.put(socketObj.cpu, socketObj);
//                break;
//            }
//            break;
//        }
//
//        case e_entry_fcntl,e_exit_fcntl:
//            socketConnexion.fcntl(event,getStateSystemBuilder());
//            break;
////        case e_entry_getsockopt,e_exit_getsockopt:
////            socketConnexion.fetch_socket_option(event,getStateSystemBuilder());
////            break;
////        case e_entry_write,e_exit_write:
////            socketConnexion.write(event, getStateSystemBuilder());
////            break;
//        case e_entry_bind,e_exit_bind:{
//            //System.out.println("#######MYCPU->"+socketObj.cpu+" MY -> FD"+socketObj.fd);
//            socketObj = socketConnexion.bind(event,getStateSystemBuilder(),maps);
//           // System.out.println("***********buy ->"+socketObj.isActive);
//            break;
//        }
//
//
////        case e_entry_accept,e_exit_accept,e_entry_accept4,e_exit_accept4:
////            socketConnexion.accept(event,getStateSystemBuilder(),maps);
////            break;
////        case e_entry_listen,e_exit_listen:
////            socketConnexion.listen(event,getStateSystemBuilder());
////            break;
//        case e_entry_connect,e_exit_connect :
//            socketConnexion.connect(event,getStateSystemBuilder());
//            break;
//        case e_entry_getsockname,e_exit_getsockname :
//            socketConnexion.getsockname(event,getStateSystemBuilder());
//            break;
////        case e_entry_setsockopt,e_exit_setsockopt:
////            socketConnexion.config_socket_option(event,getStateSystemBuilder());
////            break;
////        case e_entry_read,e_exit_read:
////            socketConnexion.read(event,getStateSystemBuilder());
////            break;
////        case e_entry_sendmsg,e_exit_sendmsg:
////            socketConnexion.sendmsg(event,getStateSystemBuilder());
////            break;
////        case e_entry_recvmsg,e_exit_recvmsg:
////            socketConnexion.recvmsg(event,getStateSystemBuilder());
////            break;
////        case e_entry_close,e_exit_close:
////            socketConnexion.close(event,getStateSystemBuilder());
////            break;
////        case e_entry_shutdown,e_exit_shutdown:
////            socketConnexion.shutdown(event,getStateSystemBuilder());
////            break;
        default:
            //System.out.println("***Other Event***");
            break;
        }
    }
}
