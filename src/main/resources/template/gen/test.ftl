"${(a == "PRI") ? string('${a}',"${a}")}"
<#list list as l>${l.a}${ l?has_next ?string(",","")}
<#if l.search! == '=' || l.search! == '1'>
1dass

</#if>
</#list>
${r'#{id}'}
