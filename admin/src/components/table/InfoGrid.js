import classNames from 'classnames'

const InfoGrid = ({ label, children }) => {
  return (
    <>
      <div className={classNames('td', 'header')}>{label}</div>
      <div className={classNames('td', 'grid')}>{children}</div>
    </>
  )
}

export default InfoGrid
