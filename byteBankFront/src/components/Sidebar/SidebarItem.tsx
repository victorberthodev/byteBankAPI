import React from 'react';
import { Link } from 'react-router-dom';
import styles from './styles.module.css'; 

interface SidebarItemProps {
  icon: React.ReactNode;
  label: string;
  to: string;
}

const SidebarItem: React.FC<SidebarItemProps> = ({ icon, label, to }) => {
  return (
    <Link to={to} className={styles.sidebarItem}>
      <div className={styles.icon}>{icon}</div>
      <span>{label}</span>
    </Link>
  );
};

export default SidebarItem;

