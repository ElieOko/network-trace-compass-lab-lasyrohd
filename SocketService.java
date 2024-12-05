package org.eclipse.tracecompass.incubator.internal.kernel.analysts.core.analysis;

import java.util.Map;
import java.util.Objects;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tracecompass.statesystem.core.ITmfStateSystemBuilder;
import org.eclipse.tracecompass.tmf.core.event.ITmfEvent;
import org.eclipse.tracecompass.tmf.core.event.aspect.TmfCpuAspect;
import org.eclipse.tracecompass.tmf.core.trace.TmfTraceUtils;

public class SocketService  {

   private static final String e_entry_socket       = "syscall_entry_socket";
   private static final String e_entry_write        = "syscall_entry_write";
   private static final String e_exit_write         = "syscall_exit_write";
   private static final String e_entry_read         = "syscall_entry_read";
   private static final String e_exit_read          = "syscall_exit_read";
   private static final String e_exit_socket        = "syscall_exit_socket";
   private static final String e_entry_bind         = "syscall_entry_bind";
   private static final String e_exit_bind          = "syscall_exit_bind";
   private static final String e_entry_listen       = "syscall_entry_listen";
   private static final String e_exit_listen        = "syscall_exit_listen";
   private static final String e_entry_accept       = "syscall_entry_accept";
   private static final String e_exit_accept        = "syscall_exit_accept";
   private static final String e_entry_connect      = "syscall_entry_connect";
   private static final String e_exit_connect       = "syscall_exit_connect";
   private static final String e_entry_setsockopt   = "syscall_entry_setsockopt";
   private static final String e_exit_setsockopt   = "syscall_exit_setsockopt";
   private static final String e_entry_getsockopt   = "syscall_entry_getsockopt";
   private static final String e_exit_getsockopt   = "syscall_exit_getsockopt";
   private static final String e_entry_sendmsg      = "syscall_entry_sendmsg";
   private static final String e_exit_sendmsg       = "syscall_exit_sendmsg";
   private static final String e_entry_recvmsg      = "syscall_entry_recvmsg";
   private static final String e_entry_close        = "syscall_entry_close";
   private static final String e_exit_close         = "syscall_exit_close";
   private static final String e_entry_shutdown     = "syscall_entry_shutdown";
   private static final String e_exit_shutdown      = "syscall_exit_shutdown";
   static SocketParams socketAllValue = new SocketParams();
//   static Map<Integer, Object> map                  = new HashMap<>();

   /***
    *
    * Creation du socket
    */


   public SocketParams  socket(ITmfEvent event,@Nullable ITmfStateSystemBuilder builder, Map<Integer, SocketParams> socketValue) {


           if(event.getName().equals(e_entry_socket)) {

               //final long ts = event.getTimestamp().getValue();
               //socketAllValue.socket = new Socket();
               //mapValue.put(1, socketAllValue);
               final long ts = event.getTimestamp().getValue();
               Integer family = event.getContent().getFieldValue(Integer.class, "family"); //$NON-NLS-1$
               Integer type = event.getContent().getFieldValue(Integer.class, "type"); //$NON-NLS-1$
               Integer protocol = event.getContent().getFieldValue(Integer.class, "protocol"); //$NON-NLS-1$
               System.out.print("*******e_entry_socket********\n ");
               Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
               socketAllValue.cpu = cpu;
               ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
               int quark = ss.getQuarkAbsoluteAndAdd("connection"); //$NON-NLS-1$
               int quark_client = ss.getQuarkRelativeAndAdd(quark,"client");
               // The main quark contains the tid of the running thread
               int quark_store = ss.getQuarkRelativeAndAdd(quark_client,"store");
               int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");
               int quark_store_value_ts= ss.getQuarkRelativeAndAdd(quark_store_value, "ts");
               //int quark_store_value_cpu= ss.getQuarkRelativeAndAdd(quark_store_value, "cpu",String.valueOf(cpu));


               ss.modifyAttribute(ts, ts, quark_store_value_ts);
           }

           if(event.getName().equals(e_exit_socket)) {
               final long ts = event.getTimestamp().getValue();
               Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
               //var sock  =socketValue.get(cpu);
               Integer ret = event.getContent().getFieldValue(Integer.class, "ret");
               if(ret != null) {
                   socketAllValue.isActive = false;
                   socketAllValue.cpu = cpu;
                     ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
                     int quark = ss.getQuarkAbsoluteAndAdd("connection"); //$NON-NLS-1$
                     int quark_client = ss.getQuarkRelativeAndAdd(quark,"client");
                     int quark_store = ss.getQuarkRelativeAndAdd(quark_client,"store");
                     int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");
                     int quark_store_value_cpu= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf(cpu));
                     int quark_store_value_cpu_ret= ss.getQuarkRelativeAndAdd(quark_store_value_cpu,"ret",String.valueOf(ret));
                     //state
                     int quark_store_value_ts= ss.getQuarkRelativeAndAdd(quark_store_value, "ts");
                     Long r_ts= (Long)ss.queryOngoing(quark_store_value_ts);

                     ss.modifyAttribute(ts, ret, quark_store_value_cpu_ret);
                     //State current
                     Integer r_ret= (Integer)ss.queryOngoing(quark_store_value_cpu_ret);
//                     System.out.println("@@@@@@@@@@@@->"+quark_store_cpu);
//                     System.out.println("###@@@@@@->"+ret);


                     if(r_ret != null && r_ts != null) {
                         System.out.println("##***###->"+r_ret);
                         int quark2 = ss.getQuarkRelativeAndAdd(quark_client,String.valueOf(r_ret));
                         //Integer value = (r_fd > 0 ? 1 : 0);
                         ss.modifyAttribute(r_ts,"socket creation",quark2 );
                         ss.modifyAttribute(ts,null, quark2);
                     }
               }
           }


       return socketAllValue;
   }

   /**
    *
    *ff
    */
   public SocketParams bind(ITmfEvent event,@Nullable ITmfStateSystemBuilder builder, Map<Integer, SocketParams> socketValue) {

          if(event.getName().equals(e_entry_bind)) {
              final long ts = event.getTimestamp().getValue();
              Integer fd = event.getContent().getFieldValue(Integer.class, "fd"); //$NON-NLS-1$

              Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
              if (cpu == null || fd == null) {
                  return null;
              }
              socketAllValue.fd = fd;
              socketAllValue.cpu = cpu;
              ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
              int quark = ss.getQuarkAbsoluteAndAdd("bind"); //$NON-NLS-1$
              // The main quark contains the tid of the running thread
              int quarkfd = ss.getQuarkRelativeAndAdd(quark,String.valueOf(fd));
              ss.modifyAttribute(ts, "bind", quarkfd);

          }
          if(event.getName().equals(e_exit_bind)) {

              final long ts = event.getTimestamp().getValue();
              Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
              var sock  =socketValue.get(cpu);
              if(sock != null) {
                  socketAllValue.isActive = false;
                  socketAllValue.cpu = cpu;
                    ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
                    int quark = ss.getQuarkAbsoluteAndAdd("bind"); //$NON-NLS-1$
                    int quark2 = ss.getQuarkRelativeAndAdd(quark,String.valueOf(sock.fd));
                    Integer value = (sock.fd > 0 ? 1 : 0);
                    ss.modifyAttribute(ts, value, quark2);
              }
          }

          return socketAllValue;
   }

   /**
    *
    * listen
    */
   public SocketParams listen(ITmfEvent event,@Nullable ITmfStateSystemBuilder builder, Map<Integer, SocketParams> socketValue) {

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
               int quark = ss.getQuarkAbsoluteAndAdd("listen_socket"); //$NON-NLS-1$
               // The main quark contains the tid of the running thread
               int quarkfd = ss.getQuarkRelativeAndAdd(quark,String.valueOf(fd));
               ss.modifyAttribute(ts, "listen_socket", quarkfd);
           }
           if(event.getName().equals(e_exit_listen)) {
               final long ts = event.getTimestamp().getValue();
               Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
               var sock  =socketValue.get(cpu);
               if(sock != null) {
                   socketAllValue.isActive = false;
                   socketAllValue.cpu = cpu;
                     ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
                     int quark = ss.getQuarkAbsoluteAndAdd("listen_socket"); //$NON-NLS-1$
                     int quark2 = ss.getQuarkRelativeAndAdd(quark,String.valueOf(sock.fd));
                     Integer value = (sock.fd > 0 ? 1 : 0);
                     ss.modifyAttribute(ts, value, quark2);
                }
           }


       return socketAllValue;
   }
   /***
    *
    * accept
    */
   public SocketParams accept(ITmfEvent event,@Nullable ITmfStateSystemBuilder builder, Map<Integer, SocketParams> socketValue) {

           if(event.getName().equals(e_entry_accept)) {
               final long ts = event.getTimestamp().getValue();
               Integer fd = event.getContent().getFieldValue(Integer.class, "fd"); //$NON-NLS-1$

               Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
               if (cpu == null || fd == null) {
                   return null;
               }
               socketAllValue.fd = fd;
               socketAllValue.cpu = cpu;
               ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
               int quark = ss.getQuarkAbsoluteAndAdd("accept_socket"); //$NON-NLS-1$
               // The main quark contains the tid of the running thread
               int quarkfd = ss.getQuarkRelativeAndAdd(quark,String.valueOf(fd));
               ss.modifyAttribute(ts, "accept_socket", quarkfd);
           }
           if(event.getName().equals(e_exit_accept)) {
               final long ts = event.getTimestamp().getValue();
               Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
               var sock  =socketValue.get(cpu);
               if(sock != null) {
                   socketAllValue.isActive = false;
                   socketAllValue.cpu = cpu;
                     ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
                     int quark = ss.getQuarkAbsoluteAndAdd("accept_socket"); //$NON-NLS-1$
                     int quark2 = ss.getQuarkRelativeAndAdd(quark,String.valueOf(sock.fd));
                     Integer value = (sock.fd > 0 ? 1 : 0);
                     ss.modifyAttribute(ts, value, quark2);
                     }
           }


       return socketAllValue;
   }

   /**
    *f
    *connect
    */
   public SocketParams connect(ITmfEvent event,@Nullable ITmfStateSystemBuilder builder, Map<Integer, SocketParams> socketValue) {

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
           // The main quark contains the tid of the running thread
           int quark_client = ss.getQuarkRelativeAndAdd(quark,"client");

           int quark_store = ss.getQuarkRelativeAndAdd(quark_client,"store");
           int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");
           int quark_store_fd_cpu= ss.getQuarkRelativeAndAdd(quark_client, "fd",String.valueOf(fd));
           int quark_store_cpu_fd= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf( cpu));
           ss.modifyAttribute(ts, cpu, quark_store_fd_cpu);
           ss.modifyAttribute(ts, fd, quark_store_cpu_fd);
           int quark_fd= ss.getQuarkRelativeAndAdd(quark_client,String.valueOf(fd));
           //int quarkfd = ss.getQuarkRelativeAndAdd(quark,String.valueOf(fd));
           ss.modifyAttribute(ts, "connection", quark_fd);

       }

       if(event.getName().equals(e_exit_connect)) {
           final long ts = event.getTimestamp().getValue();
           Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
//           var sock  =socketValue.get(cpu);
//           if(sock != null) {
               socketAllValue.isActive = false;
               socketAllValue.cpu = cpu;
                 ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);

                 int quark = ss.getQuarkAbsoluteAndAdd("connection"); //$NON-NLS-1$
                 int quark_client = ss.getQuarkRelativeAndAdd(quark,"client");
                 int quark_store = ss.getQuarkRelativeAndAdd(quark_client,"store");
                 int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");
//                 int quark_store_fd_cpu= ss.getQuarkRelativeAndAdd(quark_store_value, "fd",String.valueOf(fd));
                 int quark_store_cpu_fd= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf( cpu));

                 Integer r_fd= (Integer)ss.queryOngoing(quark_store_cpu_fd);
                 System.out.println("##################################->"+quark_store_cpu_fd);


                 if(r_fd != null) {
                     System.out.println("##################################->"+r_fd);
                     int quark2 = ss.getQuarkRelativeAndAdd(quark_client,String.valueOf(r_fd));
                     //Integer value = (r_fd > 0 ? 1 : 0);
                     ss.modifyAttribute(ts, null, quark2);
                 }

           //}
       }
        return socketAllValue;
   }
   /***
    *
    * config socket
    */
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
               int quark_client = ss.getQuarkRelativeAndAdd(quark,"client");
               int quark_fd_= ss.getQuarkRelativeAndAdd(quark_client, "fd",String.valueOf(fd));
               ss.modifyAttribute(ts, cpu, quark_fd_);
               int quark_fd= ss.getQuarkRelativeAndAdd(quark_client,String.valueOf(fd));
               ss.modifyAttribute(ts, "config_socket", quark_fd);
           }
           if(event.getName().equals(e_exit_setsockopt)) {
               ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
               final long ts = event.getTimestamp().getValue();
               Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
               int quark = ss.getQuarkAbsoluteAndAdd("connection");
               int quark_client = ss.getQuarkRelativeAndAdd(quark,"client");
               int quark_store = ss.getQuarkRelativeAndAdd(quark_client,"store");
               int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");
               int quark_store_value_cpu_fd= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf(cpu));
               Integer r_fd= (Integer)ss.queryOngoing(quark_store_value_cpu_fd);
               if(r_fd != null) {
                   System.out.println("############exi->"+r_fd);
                   int quark2 = ss.getQuarkRelativeAndAdd(quark_client,String.valueOf(r_fd));
                   //Integer value = (r_fd > 0 ? 1 : 0);
                   ss.modifyAttribute(ts, null, quark2);
               }

           }

       return socketAllValue;
   }
   /***
   *
   * fetch socket
   */
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
              int quark_client = ss.getQuarkRelativeAndAdd(quark,"client");
              int quark_fd_= ss.getQuarkRelativeAndAdd(quark_client, "fd",String.valueOf(fd));
              ss.modifyAttribute(ts, cpu, quark_fd_);
              int quark_fd= ss.getQuarkRelativeAndAdd(quark_client,String.valueOf(fd));
              ss.modifyAttribute(ts, "reccuperation_socket", quark_fd);

             // ss.modifyAttribute(ts, "recuperation_socket", quark_fd);
          }
          if(event.getName().equals(e_exit_getsockopt)) {
              final long ts = event.getTimestamp().getValue();
              Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
              ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
              int quark = ss.getQuarkAbsoluteAndAdd("connection");
              int quark_client = ss.getQuarkRelativeAndAdd(quark,"client");
              int quark_store = ss.getQuarkRelativeAndAdd(quark_client,"store");
              int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");
              int quark_store_value_cpu_fd= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf(cpu));
              Integer r_fd= (Integer)ss.queryOngoing(quark_store_value_cpu_fd);
              //Integer r_fd= (Integer)ss.queryOngoing(quark_store_cpu_fd);
             // System.out.println("##################################->"+quark_store_cpu_fd);


              if(r_fd != null) {
                  System.out.println("##################################->"+r_fd);
                  int quark2 = ss.getQuarkRelativeAndAdd(quark_client,String.valueOf(r_fd));
                  //Integer value = (r_fd > 0 ? 1 : 0);
                  ss.modifyAttribute(ts, null, quark2);
              }
          }
          return socketAllValue;
  }
  /**
   *
   *
   */
  public SocketParams sendmsg(ITmfEvent event,@Nullable ITmfStateSystemBuilder builder,Map<Integer, SocketParams> socketValue) {

          if(event.getName().equals(e_entry_sendmsg)) {
              final long ts = event.getTimestamp().getValue();
              Integer family = event.getContent().getFieldValue(Integer.class, "family"); //$NON-NLS-1$
              Integer type = event.getContent().getFieldValue(Integer.class, "type"); //$NON-NLS-1$
              Integer protocol = event.getContent().getFieldValue(Integer.class, "protocol"); //$NON-NLS-1$

              Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
              socketAllValue.cpu = cpu;
              ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
              int quark = ss.getQuarkAbsoluteAndAdd("send"); //$NON-NLS-1$
              int quarkfd = ss.getQuarkRelativeAndAdd(quark,String.valueOf(family),String.valueOf(type),String.valueOf(protocol));
              ss.modifyAttribute(ts, "send", quarkfd);
          }
          if(event.getName().equals(e_exit_sendmsg)) {
              final long ts = event.getTimestamp().getValue();
              Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
              var sock  = socketValue.get(cpu);
              if(sock != null) {
                  socketAllValue.isActive = false;
                  socketAllValue.cpu = cpu;
                    ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
                    int quark = ss.getQuarkAbsoluteAndAdd("send"); //$NON-NLS-1$
                    int quark2 = ss.getQuarkRelativeAndAdd(quark,String.valueOf(sock.fd));
                    Integer value = (sock.fd > 0 ? 1 : 0);
                    ss.modifyAttribute(ts, value, quark2);
              }
          }
          return socketAllValue;
  }
   /***
    *
    *
    */
   public SocketParams recvmsg(ITmfEvent event,@Nullable ITmfStateSystemBuilder builder) {

           if(event.getName().equals(e_entry_recvmsg)) {
               ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
           }
           return socketAllValue;
   }

   /***
   *
   *
   */
    public SocketParams shutdown(ITmfEvent event,@Nullable ITmfStateSystemBuilder builder) {


          if(event.getName().equals(e_entry_shutdown)) {
              ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
          }
         if(event.getName().equals(e_exit_shutdown)) {

           }
         return socketAllValue;
    }
    /***
    *
    *
    */
   public SocketParams close(ITmfEvent event,@Nullable ITmfStateSystemBuilder builder) {

           if(event.getName().equals(e_entry_close)) {
               final long ts = event.getTimestamp().getValue();
               Integer family = event.getContent().getFieldValue(Integer.class, "family"); //$NON-NLS-1$
               Integer type = event.getContent().getFieldValue(Integer.class, "type"); //$NON-NLS-1$
               Integer protocol = event.getContent().getFieldValue(Integer.class, "protocol"); //$NON-NLS-1$

               Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
               socketAllValue.cpu = cpu;
               ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
               Integer fd = event.getContent().getFieldValue(Integer.class, "fd");
               int quark = ss.getQuarkAbsoluteAndAdd("connection");
               int quark_client = ss.getQuarkRelativeAndAdd(quark,"client");
               int quark_store = ss.getQuarkRelativeAndAdd(quark_client,"store");
               int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");
               int quark_store_value_cpu = ss.getQuarkRelativeAndAdd(quark_store_value, "cpu", String.valueOf(cpu));
               int quark_fd_= ss.getQuarkRelativeAndAdd(quark_store_value_cpu, "fd",String.valueOf(fd));
               //int quark_store_value_cpu_count= ss.getQuarkRelativeAndAdd(quark_store_value, "count",String.valueOf(count));
               ss.modifyAttribute(ts, cpu, quark_fd_);
               //ss.modifyAttribute(ts, count, quark_store_value_cpu_count);
               int quark_fd= ss.getQuarkRelativeAndAdd(quark_client,String.valueOf(fd));
               ss.modifyAttribute(ts, "close", quark_fd);
               ss.modifyAttribute(ts, fd, quark_store_value_cpu);
           }
           if(event.getName().equals(e_exit_close)) {
               final long ts = event.getTimestamp().getValue();
               ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
               Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
               int quark = ss.getQuarkAbsoluteAndAdd("connection");
               int quark_client = ss.getQuarkRelativeAndAdd(quark,"client");

               int quark_store = ss.getQuarkRelativeAndAdd(quark_client,"store");
               int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");
//               int quark_store_fd_cpu= ss.getQuarkRelativeAndAdd(quark_store_value, "fd",String.valueOf(fd));
               int quark_store_cpu= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf( cpu));

               Integer r_fd= (Integer)ss.queryOngoing(quark_store_cpu);
               System.out.println("##################################->"+quark_store_cpu);


               if(r_fd != null) {
                   System.out.println("##################################->"+r_fd);
                   int quark2 = ss.getQuarkRelativeAndAdd(quark_client,String.valueOf(r_fd));
                   //Integer value = (r_fd > 0 ? 1 : 0);
                   ss.modifyAttribute(ts, null, quark2);
               }

           }
      return socketAllValue;
   }

   public void write(ITmfEvent event,@Nullable ITmfStateSystemBuilder builder) {

       if(event.getName().equals(e_entry_write)) {
           final long ts = event.getTimestamp().getValue();
           Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
           ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
           Integer fd = event.getContent().getFieldValue(Integer.class, "fd");
           Integer count = event.getContent().getFieldValue(Integer.class, "count");
           int quark = ss.getQuarkAbsoluteAndAdd("connection");
           int quark_client = ss.getQuarkRelativeAndAdd(quark,"client");
           int quark_store = ss.getQuarkRelativeAndAdd(quark_client,"store");
           int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");
           int quark_store_value_cpu = ss.getQuarkRelativeAndAdd(quark_store_value, "cpu", String.valueOf(cpu));
           int quark_fd_= ss.getQuarkRelativeAndAdd(quark_store_value_cpu, "fd",String.valueOf(fd));
           //int quark_store_value_cpu_count= ss.getQuarkRelativeAndAdd(quark_store_value, "count",String.valueOf(count));
           ss.modifyAttribute(ts, cpu, quark_fd_);
           //ss.modifyAttribute(ts, count, quark_store_value_cpu_count);
           int quark_fd= ss.getQuarkRelativeAndAdd(quark_client,String.valueOf(fd));
           ss.modifyAttribute(ts, "write", quark_fd);
           ss.modifyAttribute(ts, fd, quark_store_value_cpu);

       }
       if(event.getName().equals(e_exit_write)) {

           final long ts = event.getTimestamp().getValue();
           ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
          // Integer ret = event.getContent().getFieldValue(Integer.class, "ret");
           Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);

           int quark = ss.getQuarkAbsoluteAndAdd("connection"); //$NON-NLS-1$
           int quark_client = ss.getQuarkRelativeAndAdd(quark,"client");
           int quark_store = ss.getQuarkRelativeAndAdd(quark_client,"store");
           int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");
//           int quark_store_fd_cpu= ss.getQuarkRelativeAndAdd(quark_store_value, "fd",String.valueOf(fd));
           int quark_store_cpu= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf( cpu));

           Integer r_fd= (Integer)ss.queryOngoing(quark_store_cpu);
           System.out.println("##################################->"+quark_store_cpu);


           if(r_fd != null) {
               System.out.println("##################################->"+r_fd);
               int quark2 = ss.getQuarkRelativeAndAdd(quark_client,String.valueOf(r_fd));
               //Integer value = (r_fd > 0 ? 1 : 0);
               ss.modifyAttribute(ts, null, quark2);
           }
        }
   }

   public void read(ITmfEvent event,@Nullable ITmfStateSystemBuilder builder) {

       if(event.getName().equals(e_entry_read)) {
           final long ts = event.getTimestamp().getValue();
           Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
           ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
           Integer fd = event.getContent().getFieldValue(Integer.class, "fd");
           Integer count = event.getContent().getFieldValue(Integer.class, "count");
           //int quark = ss.getQuarkAbsoluteAndAdd("connection");
           int quark = ss.getQuarkAbsoluteAndAdd("connection");
           int quark_client = ss.getQuarkRelativeAndAdd(quark,"client");
           int quark_store = ss.getQuarkRelativeAndAdd(quark_client,"store");
           int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");
           int quark_store_value_cpu = ss.getQuarkRelativeAndAdd(quark_store_value, "cpu", String.valueOf(cpu));
           int quark_fd_= ss.getQuarkRelativeAndAdd(quark_store_value_cpu, "fd",String.valueOf(fd));
           //int quark_store_value_cpu_count= ss.getQuarkRelativeAndAdd(quark_store_value, "count",String.valueOf(count));
           ss.modifyAttribute(ts, cpu, quark_fd_);
           //ss.modifyAttribute(ts, count, quark_store_value_cpu_count);
           int quark_fd= ss.getQuarkRelativeAndAdd(quark_client,String.valueOf(fd));
           ss.modifyAttribute(ts, "read", quark_fd);
           ss.modifyAttribute(ts, fd, quark_store_value_cpu);
       }

       if(event.getName().equals(e_exit_read)) {
           final long ts = event.getTimestamp().getValue();
           ITmfStateSystemBuilder ss = Objects.requireNonNull(builder);
          // Integer ret = event.getContent().getFieldValue(Integer.class, "ret");
           Integer cpu = TmfTraceUtils.resolveIntEventAspectOfClassForEvent(event.getTrace(), TmfCpuAspect.class, event);
           int quark = ss.getQuarkAbsoluteAndAdd("connection"); //$NON-NLS-1$
           int quark_client = ss.getQuarkRelativeAndAdd(quark,"client");
           int quark_store = ss.getQuarkRelativeAndAdd(quark_client,"store");
           int quark_store_value= ss.getQuarkRelativeAndAdd(quark_store, "value");
//           int quark_store_fd_cpu= ss.getQuarkRelativeAndAdd(quark_store_value, "fd",String.valueOf(fd));
           int quark_store_cpu= ss.getQuarkRelativeAndAdd(quark_store_value,"cpu",String.valueOf( cpu));

           Integer r_fd= (Integer)ss.queryOngoing(quark_store_cpu);
           System.out.println("##################################->"+quark_store_cpu);


           if(r_fd != null) {
               System.out.println("##################################->"+r_fd);
               int quark2 = ss.getQuarkRelativeAndAdd(quark_client,String.valueOf(r_fd));
               //Integer value = (r_fd > 0 ? 1 : 0);
               ss.modifyAttribute(ts, null, quark2);
           }

        }
   }

}