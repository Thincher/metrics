package com.alibaba.metrics.jvm;

import com.alibaba.metrics.Gauge;
import com.alibaba.metrics.MetricName;
import org.junit.Before;
import org.junit.Test;

import java.lang.management.RuntimeMXBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JvmAttributeGaugeSetTest {
    private final RuntimeMXBean runtime = mock(RuntimeMXBean.class);
    private final JvmAttributeGaugeSet gauges = new JvmAttributeGaugeSet(runtime);

    @Before
    public void setUp() throws Exception {
        when(runtime.getName()).thenReturn("9928@example.com");

        when(runtime.getVmVendor()).thenReturn("Oracle Corporation");
        when(runtime.getVmName()).thenReturn("Java HotSpot(TM) 64-Bit Server VM");
        when(runtime.getVmVersion()).thenReturn("23.7-b01");
        when(runtime.getSpecVersion()).thenReturn("1.7");
        when(runtime.getUptime()).thenReturn(100L);
    }

    @Test
    public void hasASetOfGauges() throws Exception {
        assertThat(gauges.getMetrics().keySet())
                .containsOnly(
                        MetricName.build("vendor"),
                        MetricName.build("name"),
                        MetricName.build("uptime"));
    }

    @Test
    public void hasAGaugeForTheJVMName() throws Exception {
        final Gauge gauge = (Gauge) gauges.getMetrics().get(MetricName.build("name"));

        assertThat(gauge.getValue())
                .isEqualTo("9928@example.com");
    }

    @Test
    public void hasAGaugeForTheJVMVendor() throws Exception {
        final Gauge gauge = (Gauge) gauges.getMetrics().get(MetricName.build("vendor"));

        assertThat(gauge.getValue())
                .isEqualTo("Oracle Corporation Java HotSpot(TM) 64-Bit Server VM 23.7-b01 (1.7)");
    }

    @Test
    public void hasAGaugeForTheJVMUptime() throws Exception {
        final Gauge gauge = (Gauge) gauges.getMetrics().get(MetricName.build("uptime"));

        assertThat(gauge.getValue())
                .isEqualTo(100L);
    }

    @Test
    public void autoDiscoversTheRuntimeBean() throws Exception {
        final Gauge gauge = (Gauge) new JvmAttributeGaugeSet().getMetrics().get(MetricName.build("uptime"));

        assertThat((Long) gauge.getValue())
                .isPositive();
    }
}
