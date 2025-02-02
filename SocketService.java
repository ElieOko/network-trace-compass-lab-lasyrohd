package org.eclipse.tracecompass.incubator.internal.kernel.analysts.core.analysis;

import java.util.Map;
import java.util.Objects;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tracecompass.statesystem.core.ITmfStateSystemBuilder;
import org.eclipse.tracecompass.tmf.core.event.ITmfEvent;
import org.eclipse.tracecompass.tmf.core.event.aspect.TmfCpuAspect;
import org.eclipse.tracecompass.tmf.core.trace.TmfTraceUtils;
/**
 *
 * @author Elie Oko
 */
public class SocketService  {

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
   private static final String e_entry_setsockopt   = "syscall_entry_setsockopt";//$NON-NLS-1$
   private static final String e_exit_setsockopt    = "syscall_exit_setsockopt";//$NON-NLS-1$
   private static final String e_entry_getsockopt   = "syscall_entry_getsockopt";//$NON-NLS-1$
   private static final String e_exit_getsockopt    = "syscall_exit_getsockopt";//$NON-NLS-1$
   private static final String e_entry_getsockname  = "syscall_entry_getsockname";//$NON-NLS-1$
   private static final String e_exit_getsockname   = "syscall_exit_getsockname";//$NON-NLS-1$
   private static final String e_entry_sendmsg      = "syscall_entry_sendmsg";//$NON-NLS-1$
   private static final String e_exit_sendmsg       = "syscall_exit_sendmsg";//$NON-NLS-1$
   private static final String e_entry_recvmsg      = "syscall_entry_recvmsg";//$NON-NLS-1$
   private static final String e_exit_recvmsg       = "syscall_exit_recvmsg";//$NON-NLS-1$
   private static final String e_entry_close        = "syscall_entry_close";//$NON-NLS-1$
   private static final String e_exit_close         = "syscall_exit_close";//$NON-NLS-1$
   private static final String e_entry_shutdown     = "syscall_entry_shutdown";//$NON-NLS-1$
   private static final String e_exit_shutdown      = "syscall_exit_shutdown";//$NON-NLS-1$
   private static final String e_exit_fcntl         = "syscall_exit_fcntl";//$NON-NLS-1$
   private static final String e_entry_fcntl        = "syscall_entry_fcntl";//$NON-NLS-1$

   static SocketParams socketAllValue = new SocketParams();
   /****
    * **
    * **@author Elie Oko
    * Event Side Client
 * @param event
 * @param builder
    */
   @SuppressWarnings("javadoc")
   public void write(ITmfEvent event,@Nullable ITmfStateSystemBuilder builder) {
       if(event.getName().equals(e_entry_write)) {
           final long ts = event.getTimestamp().getValue();
           Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
           ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
           Integer fd = event.getContent().getFieldValue(Integer.class, "fd");//$NON-NLS-1$
           int quark = ss.getQuarkAbsoluteAndAdd("connection"); //$NON-NLS-1$
           int quark_client = ss.getQuarkRelativeAndAdd(quark,"client");//$NON-NLS-1$
           int quark_store = ss.getQuarkRelativeAndAdd(quark_client,"store");//$NON-NLS-1$
           int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");//$NON-NLS-1$
           int quark_store_value_cpu = ss.getQuarkRelativeAndAdd(quark_store_value, "cpu", String.valueOf(cpu));//$NON-NLS-1$
           int quark_fd_= ss.getQuarkRelativeAndAdd(quark_store_value_cpu, "fd",String.valueOf(fd));//$NON-NLS-1$
           ss.modifyAttribute(ts, cpu, quark_fd_);
           int quark_fd= ss.getQuarkRelativeAndAdd(quark_client,String.valueOf(fd));
           ss.modifyAttribute(ts, "write", quark_fd);//$NON-NLS-1$
           ss.modifyAttribute(ts, fd, quark_store_value_cpu);
       }
       if(event.getName().equals(e_exit_write)) {
           final long ts = event.getTimestamp().getValue();
           ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
           Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
           int quark = ss.getQuarkAbsoluteAndAdd("connection"); //$NON-NLS-1$
           int quark_client = ss.getQuarkRelativeAndAdd(quark,"client");//$NON-NLS-1$
           int quark_store = ss.getQuarkRelativeAndAdd(quark_client,"store");//$NON-NLS-1$
           int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");//$NON-NLS-1$
           int quark_store_cpu= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf( cpu));//$NON-NLS-1$

           Integer r_fd= (Integer)ss.queryOngoing(quark_store_cpu);
           if(r_fd != null) {
               int quark2 = ss.getQuarkRelativeAndAdd(quark_client,String.valueOf(r_fd));
               ss.modifyAttribute(ts, null, quark2);
           }
        }
   }
   @SuppressWarnings("javadoc")
   public void read(ITmfEvent event,@Nullable ITmfStateSystemBuilder builder) {
       if(event.getName().equals(e_entry_read)) {
           final long ts = event.getTimestamp().getValue();
           Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
           ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
           Integer fd = event.getContent().getFieldValue(Integer.class, "fd");//$NON-NLS-1$
           int quark = ss.getQuarkAbsoluteAndAdd("connection");//$NON-NLS-1$
           int quark_client = ss.getQuarkRelativeAndAdd(quark,"client");//$NON-NLS-1$
           int quark_store = ss.getQuarkRelativeAndAdd(quark_client,"store");//$NON-NLS-1$
           int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");//$NON-NLS-1$
           int quark_store_value_cpu = ss.getQuarkRelativeAndAdd(quark_store_value, "cpu", String.valueOf(cpu));//$NON-NLS-1$
           int quark_fd_= ss.getQuarkRelativeAndAdd(quark_store_value_cpu, "fd",String.valueOf(fd));//$NON-NLS-1$
           ss.modifyAttribute(ts, cpu, quark_fd_);
           int quark_fd= ss.getQuarkRelativeAndAdd(quark_client,String.valueOf(fd));
           ss.modifyAttribute(ts, "read", quark_fd);//$NON-NLS-1$
           ss.modifyAttribute(ts, fd, quark_store_value_cpu);
       }
       if(event.getName().equals(e_exit_read)) {
           final long ts = event.getTimestamp().getValue();
           ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
           Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
           int quark = ss.getQuarkAbsoluteAndAdd("connection"); //$NON-NLS-1$
           int quark_client = ss.getQuarkRelativeAndAdd(quark,"client");//$NON-NLS-1$
           int quark_store = ss.getQuarkRelativeAndAdd(quark_client,"store");//$NON-NLS-1$
           int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");//$NON-NLS-1$
           int quark_store_cpu= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf( cpu));//$NON-NLS-1$
           Integer r_fd= (Integer)ss.queryOngoing(quark_store_cpu);
           if(r_fd != null) {
               int quark2 = ss.getQuarkRelativeAndAdd(quark_client,String.valueOf(r_fd));
               ss.modifyAttribute(ts, null, quark2);
           }
        }
   }
   @SuppressWarnings("javadoc")
   public SocketParams socket(ITmfEvent event,@Nullable ITmfStateSystemBuilder builder,Boolean isServer) {
       if(event.getName().equals(e_entry_socket)) {
          if(isServer) {
              //System.out.println("****ekoti********Bind******ekoti*****");
          }
          final long ts = event.getTimestamp().getValue();
          Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
          socketAllValue.cpu = cpu;
          ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
          int quark = ss.getQuarkAbsoluteAndAdd("connection"); //$NON-NLS-1$
         // int quark_client = ss.getQuarkRelativeAndAdd(quark,"client");//$NON-NLS-1$

          int quark_store = ss.getQuarkRelativeAndAdd(quark,"store");//$NON-NLS-1$
          int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");//$NON-NLS-1$
          int quark_store_value_ts= ss.getQuarkRelativeAndAdd(quark_store_value, "ts");//$NON-NLS-1$
          ss.modifyAttribute(ts, ts, quark_store_value_ts);
        }
        if(event.getName().equals(e_exit_socket)) {
           final long ts = event.getTimestamp().getValue();
           Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
           Integer ret = event.getContent().getFieldValue(Integer.class, "ret");//$NON-NLS-1$
           if(ret != null) {
              socketAllValue.isActive = false;
              socketAllValue.cpu = cpu;
              ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
              int quark = ss.getQuarkAbsoluteAndAdd("connection"); //$NON-NLS-1$
              //int quark_client = ss.getQuarkRelativeAndAdd(quark,"client " + ret);//$NON-NLS-1$
              int quark_store = ss.getQuarkRelativeAndAdd(quark,"store");//$NON-NLS-1$
              int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");//$NON-NLS-1$
              int quark_store_value_cpu= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf(cpu));//$NON-NLS-1$
              int quark_store_value_cpu_ret= ss.getQuarkRelativeAndAdd(quark_store_value_cpu,"ret",String.valueOf(ret));//$NON-NLS-1$
              //state
              int quark_store_value_ts= ss.getQuarkRelativeAndAdd(quark_store_value, "ts");//$NON-NLS-1$
              Long r_ts= (Long)ss.queryOngoing(quark_store_value_ts);
              ss.modifyAttribute(ts, ret, quark_store_value_cpu_ret);
              Integer r_ret= (Integer)ss.queryOngoing(quark_store_value_cpu_ret);
              if(r_ret != null && r_ts != null) {
                 socketAllValue.fd = ret;
                 int quark2 = ss.getQuarkRelativeAndAdd(quark,"client " + ret);//$NON-NLS-1$
                 ss.modifyAttribute(r_ts,"socket creation",quark2 );//$NON-NLS-1$
                 ss.modifyAttribute(ts,null, quark2);
               }
             }
           }
       return socketAllValue;
   }


   @SuppressWarnings("javadoc")
   public void fcntl(ITmfEvent event,@Nullable ITmfStateSystemBuilder builder) {

       if(event.getName().equals(e_entry_fcntl)) {
           final long ts = event.getTimestamp().getValue();
           Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
           ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
           Integer fd = event.getContent().getFieldValue(Integer.class, "fd"); //$NON-NLS-1$
           if(cpu != null || fd != null) {
               int quark = ss.getQuarkAbsoluteAndAdd("connection"); //$NON-NLS-1$
               //int quark_client = ss.getQuarkRelativeAndAdd(quark,"client " + fd);//$NON-NLS-1$
               int quark_store = ss.getQuarkRelativeAndAdd(quark,"store");//$NON-NLS-1$
               int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");//$NON-NLS-1$
               int quark_store_fd_cpu= ss.getQuarkRelativeAndAdd(quark_store_value, "fd",String.valueOf(fd));//$NON-NLS-1$
               int quark_store_cpu_fd= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf(cpu));//$NON-NLS-1$
               ss.modifyAttribute(ts, cpu, quark_store_fd_cpu);
               ss.modifyAttribute(ts, fd, quark_store_cpu_fd);
               int quark_fd= ss.getQuarkRelativeAndAdd(quark,"client " + fd);//$NON-NLS-1$
               ss.modifyAttribute(ts, "fcntl", quark_fd);//$NON-NLS-1$
           }
       }

       if(event.getName().equals(e_exit_fcntl)) {
           final long ts = event.getTimestamp().getValue();
           Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
           socketAllValue.isActive = false;
           socketAllValue.cpu = cpu;
           ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
           int quark = ss.getQuarkAbsoluteAndAdd("connection"); //$NON-NLS-1$
           int quark_store = ss.getQuarkRelativeAndAdd(quark,"store");//$NON-NLS-1$
           int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");//$NON-NLS-1$
           int quark_store_cpu_fd= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf(cpu));//$NON-NLS-1$
           Integer r_fd= (Integer)ss.queryOngoing(quark_store_cpu_fd);
          // int quark_client = ss.getQuarkRelativeAndAdd(quark,"client " + r_fd);//$NON-NLS-1$
          // System.out.println("@@@@@napesi bino@@@@ =>"+r_fd);//$NON-NLS-1$
           if(r_fd != null) {
              // System.out.println("****enter->exit");//$NON-NLS-1$
             int quark2 = ss.getQuarkRelativeAndAdd(quark,"client " + r_fd);//$NON-NLS-1$
             ss.modifyAttribute(ts, null, quark2);
           }
       }
   }





   @SuppressWarnings("javadoc")
   public SocketParams connect(ITmfEvent event,@Nullable ITmfStateSystemBuilder builder) {

       if(event.getName().equals(e_entry_connect)) {
           final long ts = event.getTimestamp().getValue();
           Integer fd = event.getContent().getFieldValue(Integer.class, "fd"); //$NON-NLS-1$
           Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
           if (cpu == null || fd == null) {
               return null;
           }
           socketAllValue.fd = fd;
           socketAllValue.cpu = cpu;
           ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
           int quark = ss.getQuarkAbsoluteAndAdd("connection"); //$NON-NLS-1$
           //int quark_client = ss.getQuarkRelativeAndAdd(quark,"client "+fd);//$NON-NLS-1$
           int quark_store  = ss.getQuarkRelativeAndAdd(quark,"store");//$NON-NLS-1$
           int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");//$NON-NLS-1$
           int quark_client_fd = ss.getQuarkRelativeAndAdd(quark_store, "fd",String.valueOf(fd));//$NON-NLS-1$
           int quark_store_cpu_fd= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf( cpu));//$NON-NLS-1$
           ss.modifyAttribute(ts, cpu, quark_client_fd);
           ss.modifyAttribute(ts, fd, quark_store_cpu_fd);
           int quark_fd= ss.getQuarkRelativeAndAdd(quark,"client "+fd);//$NON-NLS-1$
           ss.modifyAttribute(ts, "connection", quark_fd);//$NON-NLS-1$
       }

       if(event.getName().equals(e_exit_connect)) {
           final long ts = event.getTimestamp().getValue();
           Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
           socketAllValue.isActive = false;
           socketAllValue.cpu = cpu;
           ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
           int quark = ss.getQuarkAbsoluteAndAdd("connection"); //$NON-NLS-1$
           int quark_store = ss.getQuarkRelativeAndAdd(quark,"store");//$NON-NLS-1$
           int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");//$NON-NLS-1$
           int quark_store_cpu_fd= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf(cpu));//$NON-NLS-1$
           Integer r_fd= (Integer)ss.queryOngoing(quark_store_cpu_fd);
           //int quark_client = ss.getQuarkRelativeAndAdd(quark,"client " + r_fd);//$NON-NLS-1$
           if(r_fd != null) {
             int quark2 = ss.getQuarkRelativeAndAdd(quark,"client " + r_fd);//$NON-NLS-1$
             ss.modifyAttribute(ts, null, quark2);
           }
       }
        return socketAllValue;
   }

   @SuppressWarnings("javadoc")
   public void getsockname(ITmfEvent event,@Nullable ITmfStateSystemBuilder builder) {
       if(event.getName().equals(e_entry_getsockname)) {
           Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
           final long ts = event.getTimestamp().getValue();
           Integer fd = event.getContent().getFieldValue(Integer.class, "fd"); //$NON-NLS-1$
           ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
           int quark = ss.getQuarkAbsoluteAndAdd("connection"); //$NON-NLS-1$
           //int quark_client = ss.getQuarkRelativeAndAdd(quark,"client " + fd);
           int quark_store  = ss.getQuarkRelativeAndAdd(quark,"store");//$NON-NLS-1$
           int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");//$NON-NLS-1$
           int quark_client_fd = ss.getQuarkRelativeAndAdd(quark_store_value, "fd",String.valueOf(fd));//$NON-NLS-1$
           int quark_store_cpu_fd= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf( cpu));//$NON-NLS-1$
           ss.modifyAttribute(ts, cpu, quark_client_fd);
           ss.modifyAttribute(ts, fd, quark_store_cpu_fd);
           int quark_fd= ss.getQuarkRelativeAndAdd(quark,"client " + fd);//$NON-NLS-1$
           ss.modifyAttribute(ts, "getsockname", quark_fd);//$NON-NLS-1$
           }

       if(event.getName().equals(e_exit_getsockname)) {
           final long ts = event.getTimestamp().getValue();
           Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
           socketAllValue.isActive = false;
           socketAllValue.cpu = cpu;
           ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
           int quark = ss.getQuarkAbsoluteAndAdd("connection"); //$NON-NLS-1$
           int quark_store = ss.getQuarkRelativeAndAdd(quark,"store");//$NON-NLS-1$
           int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");//$NON-NLS-1$
           int quark_store_cpu_fd= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf(cpu));//$NON-NLS-1$
           Integer r_fd= (Integer)ss.queryOngoing(quark_store_cpu_fd);
           //int quark_client = ss.getQuarkRelativeAndAdd(quark,"client " + r_fd);//$NON-NLS-1$
           if(r_fd != null) {
             int quark2 = ss.getQuarkRelativeAndAdd(quark,"client " + r_fd);//$NON-NLS-1$
             ss.modifyAttribute(ts, null, quark2);
           }
       }
   }
   @SuppressWarnings("javadoc")
   public SocketParams config_socket_option(ITmfEvent event,@Nullable ITmfStateSystemBuilder builder) {

           if(event.getName().equals(e_entry_setsockopt)) {
               final long ts = event.getTimestamp().getValue();
               Integer fd = event.getContent().getFieldValue(Integer.class, "fd"); //$NON-NLS-1$
               ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
               Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
               if (cpu == null || fd == null) {
                   return null;
               }
               int quark = ss.getQuarkAbsoluteAndAdd("connection"); //$NON-NLS-1$
               int quark_client = ss.getQuarkRelativeAndAdd(quark,"client");//$NON-NLS-1$
               int quark_fd_= ss.getQuarkRelativeAndAdd(quark_client, "fd",String.valueOf(fd));//$NON-NLS-1$
               ss.modifyAttribute(ts, cpu, quark_fd_);
               int quark_fd= ss.getQuarkRelativeAndAdd(quark_client,String.valueOf(fd));
               ss.modifyAttribute(ts, "config_socket", quark_fd);//$NON-NLS-1$
           }
           if(event.getName().equals(e_exit_setsockopt)) {
               ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
               final long ts = event.getTimestamp().getValue();
               Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
               int quark = ss.getQuarkAbsoluteAndAdd("connection");//$NON-NLS-1$
               int quark_client = ss.getQuarkRelativeAndAdd(quark,"client");//$NON-NLS-1$
               int quark_store = ss.getQuarkRelativeAndAdd(quark_client,"store");//$NON-NLS-1$
               int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");//$NON-NLS-1$
               int quark_store_value_cpu_fd= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf(cpu));//$NON-NLS-1$
               Integer r_fd= (Integer)ss.queryOngoing(quark_store_value_cpu_fd);
               if(r_fd != null) {
                   int quark2 = ss.getQuarkRelativeAndAdd(quark_client,String.valueOf(r_fd));
                   ss.modifyAttribute(ts, null, quark2);
               }

           }

       return socketAllValue;
   }
   @SuppressWarnings("javadoc")
   public SocketParams fetch_socket_option(ITmfEvent event,@Nullable ITmfStateSystemBuilder builder) {
       if(event.getName().equals(e_entry_getsockopt)) {
          final long ts = event.getTimestamp().getValue();
          Integer fd = event.getContent().getFieldValue(Integer.class, "fd"); //$NON-NLS-1$
          Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
          if (cpu == null || fd == null) {
               return null;
            }
          socketAllValue.fd = fd;
          socketAllValue.cpu = cpu;
          ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
          int quark = ss.getQuarkAbsoluteAndAdd("connection"); //$NON-NLS-1$
          int quark_client = ss.getQuarkRelativeAndAdd(quark,"client");//$NON-NLS-1$
          int quark_fd_= ss.getQuarkRelativeAndAdd(quark_client, "fd",String.valueOf(fd));//$NON-NLS-1$
          ss.modifyAttribute(ts, cpu, quark_fd_);
          int quark_fd= ss.getQuarkRelativeAndAdd(quark_client,String.valueOf(fd));
          ss.modifyAttribute(ts, "reccuperation_socket", quark_fd);//$NON-NLS-1$
          }
          if(event.getName().equals(e_exit_getsockopt)) {
              final long ts = event.getTimestamp().getValue();
              Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
              ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
              int quark = ss.getQuarkAbsoluteAndAdd("connection");//$NON-NLS-1$
              int quark_client = ss.getQuarkRelativeAndAdd(quark,"client");//$NON-NLS-1$
              int quark_store = ss.getQuarkRelativeAndAdd(quark_client,"store");//$NON-NLS-1$
              int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");//$NON-NLS-1$
              int quark_store_value_cpu_fd= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf(cpu));//$NON-NLS-1$
              Integer r_fd= (Integer)ss.queryOngoing(quark_store_value_cpu_fd);
              if(r_fd != null) {
                  int quark2 = ss.getQuarkRelativeAndAdd(quark_client,String.valueOf(r_fd));
                  ss.modifyAttribute(ts, null, quark2);
              }
          }
          return socketAllValue;
  }

   @SuppressWarnings("javadoc")
   public SocketParams sendmsg(ITmfEvent event,@Nullable ITmfStateSystemBuilder builder) {
       if(event.getName().equals(e_entry_sendmsg)) {
           final long ts = event.getTimestamp().getValue();
           Integer fd = event.getContent().getFieldValue(Integer.class, "fd"); //$NON-NLS-1$
           Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
           if (cpu == null || fd == null) {
               return null;
           }
           socketAllValue.fd = fd;
           socketAllValue.cpu = cpu;
           ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
           int quark = ss.getQuarkAbsoluteAndAdd("connection"); //$NON-NLS-1$
           int quark_client = ss.getQuarkRelativeAndAdd(quark,"client");//$NON-NLS-1$
           int quark_store = ss.getQuarkRelativeAndAdd(quark_client,"store");//$NON-NLS-1$
           int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");//$NON-NLS-1$
           int quark_store_fd_cpu= ss.getQuarkRelativeAndAdd(quark_client, "fd",String.valueOf(fd));//$NON-NLS-1$
           int quark_store_cpu_fd= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf( cpu));//$NON-NLS-1$
           ss.modifyAttribute(ts, cpu, quark_store_fd_cpu);
           ss.modifyAttribute(ts, fd, quark_store_cpu_fd);
           int quark_fd= ss.getQuarkRelativeAndAdd(quark_client,String.valueOf(fd));
           ss.modifyAttribute(ts, "sendmsg", quark_fd);//$NON-NLS-1$
       }
       if(event.getName().equals(e_exit_sendmsg)) {
           final long ts = event.getTimestamp().getValue();
           Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
           socketAllValue.isActive = false;
           socketAllValue.cpu = cpu;
           ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
           int quark = ss.getQuarkAbsoluteAndAdd("connection"); //$NON-NLS-1$
           int quark_client = ss.getQuarkRelativeAndAdd(quark,"client");//$NON-NLS-1$
           int quark_store = ss.getQuarkRelativeAndAdd(quark_client,"store");//$NON-NLS-1$
           int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");//$NON-NLS-1$
           int quark_store_cpu_fd= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf(cpu));//$NON-NLS-1$
           Integer r_fd= (Integer)ss.queryOngoing(quark_store_cpu_fd);
           if(r_fd != null) {
              int quark2 = ss.getQuarkRelativeAndAdd(quark_client,String.valueOf(r_fd));
              ss.modifyAttribute(ts, null, quark2);
           }
       }
       return socketAllValue;
 }
   @SuppressWarnings("javadoc")
   public SocketParams recvmsg(ITmfEvent event,@Nullable ITmfStateSystemBuilder builder) {
        if(event.getName().equals(e_entry_recvmsg)) {
            final long ts = event.getTimestamp().getValue();
            Integer fd = event.getContent().getFieldValue(Integer.class, "fd"); //$NON-NLS-1$
            Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
            if (cpu == null || fd == null) {
                return null;
            }
            socketAllValue.fd = fd;
            socketAllValue.cpu = cpu;
            ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
            int quark = ss.getQuarkAbsoluteAndAdd("connection"); //$NON-NLS-1$
            int quark_client = ss.getQuarkRelativeAndAdd(quark,"client");//$NON-NLS-1$
            int quark_store = ss.getQuarkRelativeAndAdd(quark_client,"store");//$NON-NLS-1$
            int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");//$NON-NLS-1$
            int quark_store_fd_cpu= ss.getQuarkRelativeAndAdd(quark_client, "fd",String.valueOf(fd));//$NON-NLS-1$
            int quark_store_cpu_fd= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf( cpu));//$NON-NLS-1$
            ss.modifyAttribute(ts, cpu, quark_store_fd_cpu);
            ss.modifyAttribute(ts, fd, quark_store_cpu_fd);
            int quark_fd= ss.getQuarkRelativeAndAdd(quark_client,String.valueOf(fd));
            ss.modifyAttribute(ts, "receivemsg", quark_fd);//$NON-NLS-1$
        }

        if(event.getName().equals(e_exit_recvmsg)) {
            final long ts = event.getTimestamp().getValue();
            Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
            socketAllValue.isActive = false;
            socketAllValue.cpu = cpu;
            ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
            int quark = ss.getQuarkAbsoluteAndAdd("connection"); //$NON-NLS-1$
            int quark_client = ss.getQuarkRelativeAndAdd(quark,"client");//$NON-NLS-1$
            int quark_store = ss.getQuarkRelativeAndAdd(quark_client,"store");//$NON-NLS-1$
            int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");//$NON-NLS-1$
            int quark_store_cpu_fd= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf(cpu));//$NON-NLS-1$
            Integer r_fd= (Integer)ss.queryOngoing(quark_store_cpu_fd);
            if(r_fd != null) {
               int quark2 = ss.getQuarkRelativeAndAdd(quark_client,String.valueOf(r_fd));
               ss.modifyAttribute(ts, null, quark2);
            }
        }
        return socketAllValue;
 }

   /****
    * **
    * **
    * Event Side Server
 * @param event
 * @param builder
 * @param socketValue
 * @return
 */
   @SuppressWarnings("javadoc")
   public SocketParams accept(ITmfEvent event,@Nullable ITmfStateSystemBuilder builder, Map<Integer, SocketParams> socketValue) {
          if(event.getName().equals(e_entry_accept) || event.getName().equals(e_entry_accept4)) {
              final long ts = event.getTimestamp().getValue();
              Boolean acceptVersion = event.getName().contains("4");//$NON-NLS-1$
              Integer fd = event.getContent().getFieldValue(Integer.class, "fd"); //$NON-NLS-1$
              Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
              if (cpu == null || fd == null) {
                  return null;
              }
              socketAllValue.fd = fd;
              socketAllValue.cpu = cpu;
              ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
              int quark = ss.getQuarkAbsoluteAndAdd("connection"); //$NON-NLS-1$
              int quark_server = ss.getQuarkRelativeAndAdd(quark,"server");//$NON-NLS-1$
              int quark_store = ss.getQuarkRelativeAndAdd(quark_server,"store");//$NON-NLS-1$
              int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");//$NON-NLS-1$
              int quark_store_fd_cpu= ss.getQuarkRelativeAndAdd(quark_server, "fd",String.valueOf(fd));//$NON-NLS-1$
              int quark_store_cpu_fd= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf( cpu));//$NON-NLS-1$
              ss.modifyAttribute(ts, cpu, quark_store_fd_cpu);
              ss.modifyAttribute(ts, fd, quark_store_cpu_fd);
              int quark_fd= ss.getQuarkRelativeAndAdd(quark_server,String.valueOf(fd));
              ss.modifyAttribute(ts, acceptVersion ? "accept4":"accept", quark_fd);//$NON-NLS-1$ //$NON-NLS-2$
          }
          if(event.getName().equals(e_exit_accept) || event.getName().equals(e_exit_accept4)) {
              final long ts = event.getTimestamp().getValue();
              Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
              socketAllValue.isActive = false;
              socketAllValue.cpu = cpu;
              ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
              int quark = ss.getQuarkAbsoluteAndAdd("connection"); //$NON-NLS-1$
              int quark_server = ss.getQuarkRelativeAndAdd(quark,"server");//$NON-NLS-1$
              int quark_store = ss.getQuarkRelativeAndAdd(quark_server,"store");//$NON-NLS-1$
              int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");//$NON-NLS-1$
              int quark_store_cpu_fd= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf(cpu));//$NON-NLS-1$
              Integer r_fd= (Integer)ss.queryOngoing(quark_store_cpu_fd);
              if(r_fd != null) {
                 int quark2 = ss.getQuarkRelativeAndAdd(quark_server,String.valueOf(r_fd));
                 ss.modifyAttribute(ts, null, quark2);
              }
          }
      return socketAllValue;
  }
   @SuppressWarnings("javadoc")
   public SocketParams bind(ITmfEvent event,@Nullable ITmfStateSystemBuilder builder,Map<Integer, SocketParams> socketValue) {

      if(event.getName().equals(e_entry_bind)) {
          System.out.println("BIND -> sys call");
          final long ts = event.getTimestamp().getValue();
          Integer fd = event.getContent().getFieldValue(Integer.class, "fd"); //$NON-NLS-1$
          Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
          if (cpu == null || fd == null) {
              return null;
          }
          socketAllValue.fd = fd;
          socketAllValue.cpu = cpu;
          socketAllValue.isActive = true;
          ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
          int quark = ss.getQuarkAbsoluteAndAdd("connection"); //$NON-NLS-1$
          int quark_client = ss.getQuarkRelativeAndAdd(quark,"client " + fd);//$NON-NLS-1$
          int quark_store = ss.getQuarkRelativeAndAdd(quark,"store");//$NON-NLS-1$
          int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");//$NON-NLS-1$
         // int quark_store_fd_cpu= ss.getQuarkRelativeAndAdd(quark_server, "fd",String.valueOf(fd));//$NON-NLS-1$
          int quark_store_cpu_fd= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf( cpu));//$NON-NLS-1$
          int quark_store_fd= ss.getQuarkRelativeAndAdd(quark_store_value,"fd",String.valueOf(fd));//$NON-NLS-1$
          ss.modifyAttribute(ts, cpu, quark_store_fd);
          ss.modifyAttribute(ts, fd, quark_store_cpu_fd);
          Integer r_fd= (Integer)ss.queryOngoing(quark_store_cpu_fd);

          //int quark_fd_client= ss.getQuarkRelativeAndAdd(quark_client,String.valueOf(fd));

          int quark_server = ss.getQuarkRelativeAndAdd(quark_client,"server " + r_fd);//$NON-NLS-1$
          //int quark_fd= ss.getQuarkRelativeAndAdd(quark_server,String.valueOf(fd));
          System.out.println("->>>>>>"+r_fd);
          ss.modifyAttribute(ts, "bind", quark_server);//$NON-NLS-1$

      }
      if(event.getName().equals(e_exit_bind)) {
          final long ts = event.getTimestamp().getValue();
          Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
          socketAllValue.isActive = true;
          socketAllValue.cpu = cpu;
          ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
          int quark = ss.getQuarkAbsoluteAndAdd("connection"); //$NON-NLS-1$

          int quark_store = ss.getQuarkRelativeAndAdd(quark,"store");//$NON-NLS-1$
          int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");//$NON-NLS-1$
          int quark_store_cpu_fd= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf(cpu));//$NON-NLS-1$
          Integer r_fd= (Integer)ss.queryOngoing(quark_store_cpu_fd);
          int quark_client = ss.getQuarkRelativeAndAdd(quark,"client " + r_fd);//$NON-NLS-1$
          //int quark_server = ss.getQuarkRelativeAndAdd(quark_client,"server " + r_fd);//$NON-NLS-1$
          if(r_fd != null) {
             int quark2 = ss.getQuarkRelativeAndAdd(quark_client,"server " + r_fd);//$NON-NLS-1$
             ss.modifyAttribute(ts, null, quark2);
          }
      }
      System.out.println("**********=>"+socketValue.size());//$NON-NLS-1$

      return socketAllValue;
}

   @SuppressWarnings("javadoc")
   public SocketParams close(ITmfEvent event,@Nullable ITmfStateSystemBuilder builder) {
      if(event.getName().equals(e_entry_close)) {
          final long ts = event.getTimestamp().getValue();
          Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
          socketAllValue.cpu = cpu;
          ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
          Integer fd = event.getContent().getFieldValue(Integer.class, "fd");//$NON-NLS-1$
          int quark = ss.getQuarkAbsoluteAndAdd("connection");//$NON-NLS-1$
          int quark_server = ss.getQuarkRelativeAndAdd(quark,"server");//$NON-NLS-1$
          int quark_store = ss.getQuarkRelativeAndAdd(quark_server,"store");//$NON-NLS-1$
          int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");//$NON-NLS-1$
          int quark_store_value_cpu = ss.getQuarkRelativeAndAdd(quark_store_value, "cpu", String.valueOf(cpu));//$NON-NLS-1$
          int quark_fd_= ss.getQuarkRelativeAndAdd(quark_store_value_cpu, "fd",String.valueOf(fd));//$NON-NLS-1$
          ss.modifyAttribute(ts, cpu, quark_fd_);
          int quark_fd= ss.getQuarkRelativeAndAdd(quark_server,String.valueOf(fd));
          ss.modifyAttribute(ts, "close", quark_fd);//$NON-NLS-1$
          ss.modifyAttribute(ts, fd, quark_store_value_cpu);
      }
      if(event.getName().equals(e_exit_close)) {
          final long ts = event.getTimestamp().getValue();
          ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
          Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
          int quark = ss.getQuarkAbsoluteAndAdd("connection");//$NON-NLS-1$
          int quark_server = ss.getQuarkRelativeAndAdd(quark,"server");//$NON-NLS-1$
          int quark_store = ss.getQuarkRelativeAndAdd(quark_server,"store");//$NON-NLS-1$
          int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");//$NON-NLS-1$
          int quark_store_cpu= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf(cpu));//$NON-NLS-1$
          Integer r_fd= (Integer)ss.queryOngoing(quark_store_cpu);
          if(r_fd != null) {
              int quark2 = ss.getQuarkRelativeAndAdd(quark_server,String.valueOf(r_fd));
              ss.modifyAttribute(ts, null, quark2);
          }

      }
 return socketAllValue;
}

   @SuppressWarnings("javadoc")
   public SocketParams listen(ITmfEvent event,@Nullable ITmfStateSystemBuilder builder) {
      if(event.getName().equals(e_entry_listen)) {
          final long ts = event.getTimestamp().getValue();
          Integer fd = event.getContent().getFieldValue(Integer.class, "fd"); //$NON-NLS-1$
          Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
          if (cpu == null || fd == null) {
              return null;
          }
          socketAllValue.fd = fd;
          socketAllValue.cpu = cpu;
          ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
          int quark = ss.getQuarkAbsoluteAndAdd("connection"); //$NON-NLS-1$
          int quark_server = ss.getQuarkRelativeAndAdd(quark,"server");//$NON-NLS-1$
          int quark_store = ss.getQuarkRelativeAndAdd(quark_server,"store");//$NON-NLS-1$
          int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");//$NON-NLS-1$
          int quark_store_fd_cpu= ss.getQuarkRelativeAndAdd(quark_server, "fd",String.valueOf(fd));//$NON-NLS-1$
          int quark_store_cpu_fd= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf( cpu));//$NON-NLS-1$
          ss.modifyAttribute(ts, cpu, quark_store_fd_cpu);
          ss.modifyAttribute(ts, fd, quark_store_cpu_fd);
          int quark_fd= ss.getQuarkRelativeAndAdd(quark_server,String.valueOf(fd));
          ss.modifyAttribute(ts, "listen", quark_fd);//$NON-NLS-1$
      }
      if(event.getName().equals(e_exit_listen)) {
          final long ts = event.getTimestamp().getValue();
          Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
          socketAllValue.isActive = false;
          socketAllValue.cpu = cpu;
          ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
          int quark = ss.getQuarkAbsoluteAndAdd("connection"); //$NON-NLS-1$
          int quark_server = ss.getQuarkRelativeAndAdd(quark,"server");//$NON-NLS-1$
          int quark_store = ss.getQuarkRelativeAndAdd(quark_server,"store");//$NON-NLS-1$
          int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");//$NON-NLS-1$
          int quark_store_cpu_fd= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf(cpu));//$NON-NLS-1$
          Integer r_fd= (Integer)ss.queryOngoing(quark_store_cpu_fd);
          if(r_fd != null) {
             int quark2 = ss.getQuarkRelativeAndAdd(quark_server,String.valueOf(r_fd));
             ss.modifyAttribute(ts, null, quark2);
          }
      }
  return socketAllValue;
}

   @SuppressWarnings("javadoc")
   public SocketParams shutdown(ITmfEvent event,@Nullable ITmfStateSystemBuilder builder) {
    if(event.getName().equals(e_entry_shutdown)) {
        final long ts = event.getTimestamp().getValue();
        Integer fd = event.getContent().getFieldValue(Integer.class, "fd"); //$NON-NLS-1$
        Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
        if (cpu == null || fd == null) {
            return null;
        }
        socketAllValue.fd = fd;
        socketAllValue.cpu = cpu;
        ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
        int quark = ss.getQuarkAbsoluteAndAdd("connection"); //$NON-NLS-1$
        int quark_server = ss.getQuarkRelativeAndAdd(quark,"server");//$NON-NLS-1$
        int quark_store = ss.getQuarkRelativeAndAdd(quark_server,"store");//$NON-NLS-1$
        int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");//$NON-NLS-1$
        int quark_store_fd_cpu= ss.getQuarkRelativeAndAdd(quark_server, "fd",String.valueOf(fd));//$NON-NLS-1$
        int quark_store_cpu_fd= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf( cpu));//$NON-NLS-1$
        ss.modifyAttribute(ts, cpu, quark_store_fd_cpu);
        ss.modifyAttribute(ts, fd, quark_store_cpu_fd);
        int quark_fd= ss.getQuarkRelativeAndAdd(quark_server,String.valueOf(fd));
        ss.modifyAttribute(ts, "shutdown", quark_fd);//$NON-NLS-1$
    }
   if(event.getName().equals(e_exit_shutdown)) {
       final long ts = event.getTimestamp().getValue();
       Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
       socketAllValue.isActive = false;
       socketAllValue.cpu = cpu;
       ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
       int quark = ss.getQuarkAbsoluteAndAdd("connection"); //$NON-NLS-1$
       int quark_server = ss.getQuarkRelativeAndAdd(quark,"server");//$NON-NLS-1$
       int quark_store = ss.getQuarkRelativeAndAdd(quark_server,"store");//$NON-NLS-1$
       int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");//$NON-NLS-1$
       int quark_store_cpu_fd= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf(cpu));//$NON-NLS-1$
       Integer r_fd= (Integer)ss.queryOngoing(quark_store_cpu_fd);
       if(r_fd != null) {
          int quark2 = ss.getQuarkRelativeAndAdd(quark_server,String.valueOf(r_fd));
          ss.modifyAttribute(ts, null, quark2);
       }
     }
   return socketAllValue;
}
   @SuppressWarnings("javadoc")
   public void netdev(ITmfEvent event,@Nullable ITmfStateSystemBuilder builder) {

       if(event.getName().equals("net_dev_queue")) {
           Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
           String network = event.getContent().getFieldValue(String.class, "network_header"); //$NON-NLS-1$
           String network_type = event.getContent().getFieldValue(String.class, "network_header_type"); //$NON-NLS-1$
           if(network_type != null) {
               if(network_type.equals("_ipv4") ) {
                  // System.out.println(network_type);
                   if(network != null) {
                       int index = network.indexOf("transport_header=tcp=[");
                       int indexSaddr = network.indexOf("saddr=[");
                       if(index != -1) {
                           String header_net = network.substring(index + "transport_header=tcp=[".length());
                           int i_port = network.indexOf("source_port=");
                           String source_port = (network.substring(i_port + "source_port=".length())).split(",")[0];
                           int i_port_dest = network.indexOf("dest_port=");
                           String dest_port = (network.substring(i_port_dest + "dest_port=".length())).split(",")[0];
                           if(dest_port.equals("8500")) {
                               if (indexSaddr != -1) {
                                   header_net = network.substring(indexSaddr +"saddr=[".length());
                                   System.out.println("********SADDR********");
                                   String extractedPart = header_net.substring(0, header_net.indexOf("],"));
                                   System.out.println(extractedPart);
                               }
                               System.out.println("Destination Port " + dest_port);
                               System.out.println("Source Port " + source_port);

                           }

                           }


               }
           }



//               String[] parts = network.split(",");
             //  System.out.println(network.);
//               System.out.println(parts[1]);
//               System.out.println(parts[2]);
           }

       }

   }

}
