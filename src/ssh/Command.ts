export const cpuUsage = `top -bn 1 -i | awk -F ',' 'NR == 3 {print $4}' | awk '{printf "CPU: \{ used: %s \}\\n", 100-$1}'`

export const ramUsage = `top -bn 1 -i | awk '{if (NR == 4) printf "RAM: { free: %s, used: %s, cache: %s }\\n", $6, $8, $10}'`

export const netUsage = `old="$(</sys/class/net/eth0/statistics/tx_bytes)"; $(sleep 1); now=$(</sys/class/net/eth0/statistics/tx_bytes); awk -v now=$now -v old=$old 'BEGIN{printf "%.2fkb/s\\n", (now-old)/1024*8}'`

export const diskUsage = `iostat -d | awk '{if (NR == 4) printf "ROM: \{ read: %s, write: %s \}\\n", $3, $4}'`

const getNetworkCard = () => {
  return `ls /sys/class/net`
}

const getNetAutoUnitUsage = (inOrOut: 'rx'|'tx', NIC = 'eth0', interval = '1') => {
  return `old="$(</sys/class/net/${NIC}/statistics/${inOrOut}_bytes)"; $(sleep ${interval}); now=$(</sys/class/net/${NIC}/statistics/${inOrOut}_bytes); awk -v now=$now -v old=$old 'BEGIN {kbs = (now - old)/1024*8; if (kbs/1024 >= 1000) printf "%.2fGb/s\\n", kbs/1024/1024; else if (kbs >= 1000) printf "%.2fMb/s\\n", kbs/1024; else printf "%.2fKb/s\\n", kbs}'`
}


const getNetBytesUsage = (inOrOut: 'rx'|'tx', NIC = 'eth0', interval = '1') => {
  return `old="$(</sys/class/net/${NIC}/statistics/${inOrOut}_bytes)"; $(sleep ${interval}); echo \`expr $(</sys/class/net/${NIC}/statistics/${inOrOut}_bytes) - $old\``
}

const getNetUsage = (NIC = 'eth0', interval = '0.5') => {
  return `oldTx="$(</sys/class/net/${NIC}/statistics/tx_bytes)" oldRx="$(</sys/class/net/${NIC}/statistics/rx_bytes)"; $(sleep ${interval}); echo "NET: { tx: \`expr $(</sys/class/net/${NIC}/statistics/tx_bytes) - $oldTx\`, rx: \`expr $(</sys/class/net/${NIC}/statistics/rx_bytes) - $oldRx\` }"`
}

const shellAllInOne = (NIC = 'eth0', interval = '0.5') => {
  return cpuUsage + '&&' + ramUsage + '&&' + getNetUsage('eth0', '0.5') + '&&' + diskUsage
}

export {
  shellAllInOne
}
