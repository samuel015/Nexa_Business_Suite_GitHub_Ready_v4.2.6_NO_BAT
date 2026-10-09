(()=>{'use strict';
const $=id=>document.getElementById(id), $$=s=>Array.from(document.querySelectorAll(s));
const esc=v=>String(v??'').replace(/[&<>'"]/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;',"'":'&#39;','"':'&quot;'}[c]));
const money=v=>new Intl.NumberFormat('id-ID',{style:'currency',currency:'IDR',maximumFractionDigits:0}).format(Number(v||0));
const date=v=>{if(!v)return '-';try{return new Intl.DateTimeFormat('id-ID',{dateStyle:'medium',timeStyle:'short'}).format(new Date(v))}catch{return String(v)}};
const num=v=>new Intl.NumberFormat('id-ID').format(Number(v||0));
function csrf(){const m=document.cookie.match(/(?:^|; )XSRF-TOKEN=([^;]+)/);return m?decodeURIComponent(m[1]):''}
async function api(url,opt={}){const method=String(opt.method||'GET').toUpperCase(),token=csrf(),headers={'Content-Type':'application/json',...(opt.headers||{})};if(!['GET','HEAD','OPTIONS'].includes(method)&&token)headers['X-XSRF-TOKEN']=token;const r=await fetch(url,{credentials:'same-origin',headers,...opt});let d=null;try{d=await r.json()}catch{}if(r.status===401){location.replace('/login');throw new Error('Sesi berakhir. Silakan login kembali.')}if(!r.ok)throw new Error(d?.message||`Request gagal (${r.status})`);return d}
function toast(msg,error=false){const e=$('toast');if(!e)return;e.textContent=msg;e.className='toast show'+(error?' error':'');clearTimeout(window.__toastTimer);window.__toastTimer=setTimeout(()=>e.className='toast',3200)}
function setToday(){const e=$('todayLabel');if(e)e.textContent=new Intl.DateTimeFormat('id-ID',{dateStyle:'full'}).format(new Date())}
function setupShell(user){if($('currentUser'))$('currentUser').textContent=user.displayName||user.username;if($('currentRole'))$('currentRole').textContent=user.jobTitle||user.username;if($('avatar'))$('avatar').textContent=(user.displayName||user.username).slice(0,1).toUpperCase();const logout=$('logoutButton');if(logout)logout.onclick=async()=>{try{await api('/api/auth/logout',{method:'POST'})}finally{location.replace('/login')}};const mob=$('mobileMenu');if(mob)mob.onclick=()=>document.querySelector('.sidebar')?.classList.toggle('open')}
async function requireAccount(username){const u=await api('/api/auth/me');if(u.username!==username){location.replace(u.redirect||'/login');return null}setupShell(u);return u}
function nav(meta,onShow){$$('.nav-link').forEach(b=>b.onclick=()=>show(b.dataset.section));function show(section){$$('.page-section').forEach(s=>s.classList.toggle('active',s.dataset.page===section));$$('.nav-link').forEach(b=>b.classList.toggle('active',b.dataset.section===section));const m=meta[section];if(m){if($('pageKicker'))$('pageKicker').textContent=m[0];if($('pageTitle'))$('pageTitle').textContent=m[1];if($('pageSubtitle'))$('pageSubtitle').textContent=m[2]}document.querySelector('.sidebar')?.classList.remove('open');onShow?.(section);window.scrollTo({top:0,behavior:'smooth'})}return show}
function tableEmpty(cols,text){return `<tr><td colspan="${cols}" class="empty-state">${esc(text)}</td></tr>`}
function status(text,kind='success'){return `<span class="status ${kind}">${esc(text)}</span>`}
function stockStatus(n){n=Number(n||0);return n===0?status('Habis','danger'):n<=5?status('Kritis','warning'):status('Aman','success')}
function download(name,text,type='text/plain'){const b=new Blob([text],{type}),a=document.createElement('a');a.href=URL.createObjectURL(b);a.download=name;a.click();setTimeout(()=>URL.revokeObjectURL(a.href),500)}
function csv(name,rows){download(name,rows.map(r=>r.map(v=>'"'+String(v??'').replaceAll('"','""')+'"').join(',')).join('\r\n'),'text/csv;charset=utf-8')}
window.Nexa={$,$$,esc,money,date,num,api,toast,setToday,setupShell,requireAccount,nav,tableEmpty,status,stockStatus,download,csv,csrf};
})();
