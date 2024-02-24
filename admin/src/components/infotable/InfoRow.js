import classNames from 'classnames'

const InfoRow = ({ children }) => {
  return (
    <div className={classNames('tr')}>
      {children}
    </div>
  )
}

export default InfoRow
