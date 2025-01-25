import React from 'react';
import SidebarItem from './SidebarItem';
import { MdDashboard, MdTransferWithinAStation, MdReceipt, MdOutlineHome  } from 'react-icons/md';
import styles from './styles.module.css';
import { FaRegUserCircle } from "react-icons/fa";
import { IoMdSearch } from "react-icons/io";

const Sidebar: React.FC = () => {
  return (
    <div className={styles.sidebar}>
      <SidebarItem icon={<MdOutlineHome  />} label="HOME" to="/" />
      <SidebarItem icon={<MdDashboard />} label="CADASTRO" to="/cadastro" />
      <SidebarItem icon={<MdTransferWithinAStation />} label="TRANSAÇÕES" to="/transferencias" />
      <SidebarItem icon={<MdReceipt />} label="EXTRATO" to="/extrato" />
      <SidebarItem icon={<FaRegUserCircle />} label="MEU PERFIL" to="/conta" />
      <SidebarItem icon={<IoMdSearch />} label="BUSCA" to="/busca" />
    </div>
  );
};

export default Sidebar;
