import classNames from 'classnames'

const BtnGroup = ({ children }) => {
  return (
    <div className={classNames('btn-bottom')}>
      {children}
    </div>
  )
}

export default BtnGroup
