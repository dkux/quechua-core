import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPeriodoAdministrativo, defaultValue } from 'app/shared/model/periodo-administrativo.model';

export const ACTION_TYPES = {
  FETCH_PERIODOADMINISTRATIVO_LIST: 'periodoAdministrativo/FETCH_PERIODOADMINISTRATIVO_LIST',
  FETCH_PERIODOADMINISTRATIVO: 'periodoAdministrativo/FETCH_PERIODOADMINISTRATIVO',
  CREATE_PERIODOADMINISTRATIVO: 'periodoAdministrativo/CREATE_PERIODOADMINISTRATIVO',
  UPDATE_PERIODOADMINISTRATIVO: 'periodoAdministrativo/UPDATE_PERIODOADMINISTRATIVO',
  DELETE_PERIODOADMINISTRATIVO: 'periodoAdministrativo/DELETE_PERIODOADMINISTRATIVO',
  RESET: 'periodoAdministrativo/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPeriodoAdministrativo>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PeriodoAdministrativoState = Readonly<typeof initialState>;

// Reducer

export default (state: PeriodoAdministrativoState = initialState, action): PeriodoAdministrativoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PERIODOADMINISTRATIVO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PERIODOADMINISTRATIVO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PERIODOADMINISTRATIVO):
    case REQUEST(ACTION_TYPES.UPDATE_PERIODOADMINISTRATIVO):
    case REQUEST(ACTION_TYPES.DELETE_PERIODOADMINISTRATIVO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PERIODOADMINISTRATIVO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PERIODOADMINISTRATIVO):
    case FAILURE(ACTION_TYPES.CREATE_PERIODOADMINISTRATIVO):
    case FAILURE(ACTION_TYPES.UPDATE_PERIODOADMINISTRATIVO):
    case FAILURE(ACTION_TYPES.DELETE_PERIODOADMINISTRATIVO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PERIODOADMINISTRATIVO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PERIODOADMINISTRATIVO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PERIODOADMINISTRATIVO):
    case SUCCESS(ACTION_TYPES.UPDATE_PERIODOADMINISTRATIVO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PERIODOADMINISTRATIVO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/periodo-administrativos';

// Actions

export const getEntities: ICrudGetAllAction<IPeriodoAdministrativo> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PERIODOADMINISTRATIVO_LIST,
  payload: axios.get<IPeriodoAdministrativo>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPeriodoAdministrativo> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PERIODOADMINISTRATIVO,
    payload: axios.get<IPeriodoAdministrativo>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPeriodoAdministrativo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PERIODOADMINISTRATIVO,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPeriodoAdministrativo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PERIODOADMINISTRATIVO,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPeriodoAdministrativo> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PERIODOADMINISTRATIVO,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
